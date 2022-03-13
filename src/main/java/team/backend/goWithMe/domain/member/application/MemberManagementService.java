package team.backend.goWithMe.domain.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.MemberQueryRepository;
import team.backend.goWithMe.domain.member.domain.persist.MemberRepository;
import team.backend.goWithMe.domain.member.domain.vo.UserEmail;
import team.backend.goWithMe.domain.member.domain.vo.UserName;
import team.backend.goWithMe.domain.member.dto.FindAllResponse;
import team.backend.goWithMe.domain.member.dto.JoinResponseDTO;
import team.backend.goWithMe.domain.member.dto.MemberResponseDTO;
import team.backend.goWithMe.domain.member.error.exception.DuplicateEmailException;
import team.backend.goWithMe.domain.member.error.exception.MemberNotFoundException;
import team.backend.goWithMe.global.common.TokenDTO;
import team.backend.goWithMe.global.common.TokenProvider;
import team.backend.goWithMe.global.error.exception.ErrorCode;

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
    private final AuthenticationManagerBuilder managerBuilder;

    // 회원 가입
    public JoinResponseDTO create(final Member member) {
        member.encode(encoder);

        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicateEmailException(ErrorCode.EMAIL_DUPLICATION);
        }

        return JoinResponseDTO.from(memberRepository.save(member));
    }

    // 업데이트
    public TokenDTO update(final Member member, final UserEmail email) {
        Member findMember = memberRepository.findByEmail(email).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Member updatedMember = findMember.update(member, encoder);

        // 새로운 email을 context 안에 저장
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                (updatedMember.getEmail().userEmail(), member.getPassword().userPassword());

        Authentication authenticate = managerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return tokenProvider.createToken(updatedMember.getEmail().userEmail(), authenticate);
    }

    // 회원 삭제
    public void delete(final UserEmail email) {
        memberRepository.deleteByEmail(email);
    }

    // 회원 조회
    @Transactional(readOnly = true)
    public MemberResponseDTO findOne(final UserEmail email) {
        log.debug("userEmail : {}", email.userEmail());

        Member findMember = memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        });

        return MemberResponseDTO.create(findMember);
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<FindAllResponse> findAll(final UserEmail email, final Pageable pageable) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
            new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getPreference() != null) {
            return memberQueryRepository.findAllByFavorite(member.getPreference(), pageable).stream()
                    .map(FindAllResponse::of)
                    .collect(Collectors.toList());
        }

        return memberRepository.findAll(pageable).stream()
                .map(FindAllResponse::of)
                .collect(Collectors.toList());
    }

    // 회원 검색
    public List<FindAllResponse> searchMember(final UserName name, final Pageable pageable) {
        List<Member> members = memberRepository.findByName(name, pageable);

        return members.stream()
                .map(FindAllResponse::of)
                .collect(Collectors.toList());
    }
}
