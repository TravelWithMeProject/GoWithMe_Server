package team.backend.goWithMe.domain.Mate.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team.backend.goWithMe.domain.mate.domain.persist.MateList;
import team.backend.goWithMe.domain.mate.domain.vo.MateEmail;
import team.backend.goWithMe.domain.mate.domain.vo.MateNickName;
import team.backend.goWithMe.domain.mate.domain.vo.MateProfileImg;
import team.backend.goWithMe.domain.mate.repository.MateListRepository;
import team.backend.goWithMe.domain.mate.service.MateService;

@ExtendWith(MockitoExtension.class)
public class MateServiceTest {

    @InjectMocks
    private MateService mateService;

    @Mock
    private MateListRepository mateListRepository;

    @DisplayName("친구 저장 성공")
    @Test
    public void mateSaveTest() throws Exception {
        //given

        //when

        //then
    }

    @DisplayName("친구 id로 조회 성공")
    @Test
    public void findByIdTest() throws Exception {
        //given


        //when

        //then
    }

    @DisplayName("친구 모두 조회 성공")
    @Test
    public void findAllTest() throws Exception {
        //given


        //when

        //then
    }

    @DisplayName("친구 id로 삭제 성공")
    @Test
    public void deleteTest1() throws Exception {
        //given


        //when

        //then
    }

    @DisplayName("친구 id로 삭제 실패")
    @Test
    public void deleteTest2() throws Exception {
        //given


        //when

        //then
    }

    @DisplayName("친구 수정 성공")
    @Test
    public void updateTest1() throws Exception {
        //given


        //when

        //then
    }

    @DisplayName("친구 수정 실패")
    @Test
    public void updateTest2() throws Exception {
        //given


        //when

        //then
    }

    private MateList mateList() {
        return MateList.builder().mateEmail(MateEmail.from("qwe123@gmail.com"))
                .mateNickName(MateNickName.from("abc"))
                .mateProfileImg(MateProfileImg.from("qwe.jpg"))
                .build();
    }
}
