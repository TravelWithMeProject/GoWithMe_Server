package team.backend.goWithMe.domain.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.MemberRepository;
import team.backend.goWithMe.domain.member.dto.JoinResponseDTO;
import team.backend.goWithMe.domain.member.dto.MemberResponseDTO;
import team.backend.goWithMe.domain.member.error.exception.DuplicateEmailException;
import team.backend.goWithMe.domain.member.error.exception.MemberNotFoundException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberManagementService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

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
    public void update(final Member member, final Long memberId) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicateEmailException(ErrorCode.EMAIL_DUPLICATION);
        }

        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        findMember.update(member);
    }

    // 회원 삭제
    public void delete(final Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        });

        memberRepository.delete(member);
    }

    // 회원 조회
    @Transactional(readOnly = true)
    public MemberResponseDTO findOne(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        });

        return MemberResponseDTO.create(findMember);
    }
}
