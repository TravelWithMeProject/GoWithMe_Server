package team.backend.goWithMe.domain.member.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.backend.goWithMe.domain.auth.details.MemberDetails;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.MemberRepository;
import team.backend.goWithMe.domain.member.dto.JoinResponseDTO;
import team.backend.goWithMe.domain.member.error.exception.DuplicateEmailException;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberManagementService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    // 회원 가입
    public JoinResponseDTO create(MemberDetails member) {
        log.info("ServiceEmail : {}", member.getUsername());

        Member newMember = member.toEntity();
        newMember.encode(encoder);

        if (memberRepository.existsByEmail(newMember.getEmail())) {
            throw new DuplicateEmailException(ErrorCode.EMAIL_DUPLICATION);
        }

        Member savedMember = memberRepository.save(newMember);

        return JoinResponseDTO.from(savedMember);
    }

    // 업데이트
    public Long update(final Member member) {
        Member updateMember = member.update(member.getEmail(), member.getPassword(),
                member.getNickname(), member.getProfileImage());

        return updateMember.getId();
    }
}
