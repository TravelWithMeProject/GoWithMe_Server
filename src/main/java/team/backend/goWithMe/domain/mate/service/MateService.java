package team.backend.goWithMe.domain.mate.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.backend.goWithMe.domain.mate.domain.persist.MateList;

import team.backend.goWithMe.domain.mate.dto.MateResponseDto;
import team.backend.goWithMe.domain.mate.dto.MateRequestDto;
import team.backend.goWithMe.domain.mate.error.exception.NoSuchMateException;
import team.backend.goWithMe.domain.mate.repository.MateListRepository;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.global.error.exception.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MateService {

    private final MateListRepository mateListRepository;
    private final MemberRepository memberRepository;

    public List<MateResponseDto> findAll() {
        return mateListRepository.findAll()
                .stream()
                .map(MateResponseDto::new)
                .collect(Collectors.toList());
    }

    public MateResponseDto findById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchMateException(ErrorCode.NO_SUCH_MEMBER_IN_MATE));
        MateList entity = mateListRepository.findById(member.getId()).orElseThrow(()-> new NoSuchMateException(ErrorCode.NO_SUCH_MATE));
        return new MateResponseDto(entity);
    }

    @Transactional
    public void delete(Long mateListId) {
        MateList mateList = mateListRepository.findById(mateListId).orElseThrow(()-> new NoSuchMateException(ErrorCode.NO_SUCH_MATE));
        mateListRepository.delete(mateList);
    }

    @Transactional
    public Long save(MateRequestDto dto) {
        Member member = memberRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchMateException(ErrorCode.NO_SUCH_MEMBER_IN_MATE));
        return mateListRepository.save(dto.toEntity(member)).getId();
    }

    @Transactional
    public void update(Long id, MateRequestDto dto) {
        Member member = memberRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchMateException(ErrorCode.NO_SUCH_MEMBER_IN_MATE));
        MateList mateList = mateListRepository.findById(id).orElseThrow(()-> new NoSuchMateException(ErrorCode.NO_SUCH_MATE));
        mateList.updateMateList(mateList.getMateEmail(), mateList.getMateNickname(), mateList.getMateProfileImg(), member);
    }
}
