package team.backend.goWithMe.domain.member.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import team.backend.goWithMe.domain.member.application.MemberManagementService;
import team.backend.goWithMe.domain.member.domain.persist.Member;
import team.backend.goWithMe.domain.member.domain.persist.RoleType;
import team.backend.goWithMe.domain.member.domain.util.GivenMember;
import team.backend.goWithMe.domain.member.dto.*;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.*;
import static team.backend.goWithMe.domain.member.domain.util.GivenMember.*;

@AutoConfigureMockMvc
@SpringBootTest
class MemberManagementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberManagementService memberManagementService;

    @Autowired
    ObjectMapper objectMapper;

    static Member member = GivenMember.createMember();

    @Test
    @DisplayName("회원 생성 인수 테스트")
    void createMember() throws Exception {

        JoinRequestDTO requestMember = JoinRequestDTO.from(member);

        JoinResponseDTO responseMember = JoinResponseDTO.from(member);

        String body = objectMapper.writeValueAsString(requestMember);

        when(memberManagementService.create(any())).thenReturn(responseMember);

        mockMvc.perform(post("/api/v1/members/join").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 조회 인수 테스트")
    @WithMockUser(username = "sarr@naver.com", roles = "USER")
    void findMember() throws Exception {
        MemberResponseDTO memberResponse = MemberResponseDTO.create(member);

        when(memberManagementService.findOne(any())).thenReturn(memberResponse);

        mockMvc.perform(get("/api/v1/members/findByEmail")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 수정 인수 테스트")
    @WithMockUser(username = "ssar@naver.com", roles = "USER")
    void updateMember() throws Exception {
        String body = objectMapper.writeValueAsString(MemberUpdateDTO.from(member));

        mockMvc.perform(patch("/api/v1/members").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 삭제 인수 테스트")
    @WithMockUser(username = "ssar@naver.com", roles = "USER")
    void deleteMember() throws Exception {
        mockMvc.perform(delete("/api/v1/members"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 검색 인수 테스트")
    @WithMockUser(username = "ssar@naver.com", roles = "USER")
    void findAllMember() throws Exception {
        List<FindAllResponse> memberResponses = List.of(FindAllResponse.of(member));

        when(memberManagementService.findAll(any(), any())).thenReturn(memberResponses);

        mockMvc.perform(get("/api/v1/members")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 검색 인수 테스트")
    @WithMockUser(username = "ssar@naver.com", roles = "USER")
    void searchMember() throws Exception {
        List<FindAllResponse> memberResponses = List.of(FindAllResponse.of(member));

        when(memberManagementService.searchMember(any(), any())).thenReturn(memberResponses);

        mockMvc.perform(get("/api/v1/members/search/{name}", member.getName().userName())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}