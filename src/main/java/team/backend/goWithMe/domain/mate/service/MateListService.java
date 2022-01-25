package team.backend.goWithMe.domain.mate.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.backend.goWithMe.domain.mate.domain.persist.MateList;
import team.backend.goWithMe.domain.mate.repository.MateListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MateListService {

    private final MateListRepository mateListRepository;

    public List<MateList> findAll() {
        List<MateList> mateLists = new ArrayList<>();
        mateListRepository.findAll().forEach(m->mateLists.add(m));
        return mateLists;
    }

    public Optional<MateList> findById(Long mateListId) {
        Optional<MateList> mateList = mateListRepository.findById(mateListId);
        return mateList;
    }

    public void deleteById(Long mateListId) {
        mateListRepository.deleteById(mateListId);
    }

    public MateList save(MateList mateList) {
        mateListRepository.save(mateList);
        return mateList;
    }

    public void updateById(Long mateListId, MateList updateMateList) {
        Optional<MateList> mateList = mateListRepository.findById(mateListId);
        if(mateList.isPresent()) {
            mateList.get().updateMateList(updateMateList.getMateEmail(), updateMateList.getMateNickname(),
                    updateMateList.getMateProfileImg());
            mateListRepository.save(mateList.get());
        }
    }
}
