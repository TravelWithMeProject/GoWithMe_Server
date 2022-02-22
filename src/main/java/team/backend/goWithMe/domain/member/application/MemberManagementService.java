package team.backend.goWithMe.domain.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.MemberQueryRepository;
import team.backend.goWithMe.domain.member.domain.persist.MemberRepository;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.member.dto.JoinResponseDTO;
import team.backend.goWithMe.domain.member.dto.MemberResponseDTO;
import team.backend.goWithMe.domain.member.error.exception.DuplicateEmailException;
import team.backend.goWithMe.domain.member.error.exception.MemberNotFoundException;
import team.backend.goWithMe.global.common.AccessToken;
import team.backend.goWithMe.global.common.TokenDTO;
import team.backend.goWithMe.global.common.TokenProvider;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberManagementService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;

    // 회원 가입
    public JoinResponseDTO create(final Member member) {
        member.encode(encoder);

        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicateEmailException(ErrorCode.EMAIL_DUPLICATION);
        }

        Member savedMember = memberRepository.save(member);

        return JoinResponseDTO.from(savedMember);
    }

    // 업데이트
    public void update(final Member member, final AccessToken accessToken) {
        String email = getEmail(accessToken);

        Member findMember = memberRepository.findByEmail(UserEmail.from(email)).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        findMember.update(member);
        findMember.encode(encoder);
    }

    // 회원 삭제
    public void delete(final AccessToken accessToken) {
        String email = getEmail(accessToken);

        memberRepository.deleteByEmail(UserEmail.from(email));
    }

    // 회원 조회
    @Transactional(readOnly = true)
    public MemberResponseDTO findOne(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        });

        return MemberResponseDTO.create(findMember);
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<MemberResponseDTO> findAll(final Long id, final Pageable pageable) {
        List<Member> members;

        Member member = memberRepository.findById(id).orElseThrow(() ->
            new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getFavorite() != null) {
            members = memberQueryRepository.findAllByFavorite(member.getFavorite());
        }
        else {
            members = memberRepository.findAll();
        }

        return members.stream()
                .map(MemberResponseDTO::create)
                .collect(Collectors.toList());
    }

    private String getEmail(AccessToken accessToken) {
        Authentication authentication = tokenProvider.getAuthentication(accessToken.accessToken());
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return principal.getUsername();
    }
}
