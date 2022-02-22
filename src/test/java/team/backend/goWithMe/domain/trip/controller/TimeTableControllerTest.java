package team.backend.goWithMe.domain.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.backend.goWithMe.domain.trip.dto.request.TimeTableCreateDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableIdDTO;
import team.backend.goWithMe.domain.trip.dto.response.TimeTableListDTO;
import team.backend.goWithMe.domain.trip.service.TimeTableService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class TimeTableControllerTest {

    @InjectMocks
    private TimeTableController timeTableController;

    @Mock
    private TimeTableService timeTableService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private final Long MEMBER_ID = 1L;
    private final String TIMETABLE_NAME = "시간표 제목";
    private final String TIMETABLE_CONTENT = "시간표 내용 is ...";
    private final LocalDateTime TIMETABLE_PERIOD_START = LocalDateTime.of(2022, 1, 1, 0, 0);
    private final LocalDateTime TIMETABLE_PERIOD_END = LocalDateTime.of(2022, 1, 5, 0, 0);

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(timeTableController)
                .build();
    }

    @Test
    @DisplayName("시간표 저장 테스트")
    public void saveTimeTableSuccessTest() throws Exception {
        // given
        TimeTableCreateDTO timeTableDTO = new TimeTableCreateDTO(MEMBER_ID, TIMETABLE_NAME,
                TIMETABLE_CONTENT, TIMETABLE_PERIOD_START, TIMETABLE_PERIOD_END);
        Long sampleTimeTableId = 1L;
        when(timeTableService.createTimeTable(any())).thenReturn(sampleTimeTableId);


        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/timeTable/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timeTableDTO)));

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo("{\"success\":true,\"timeTableId\":"+ sampleTimeTableId +"}");
    }

//    @Test
//    @DisplayName("시간표 저장 테스트 - 실패 케이스")
//    public void saveTimeTableFailTest() throws Exception {
//        TimeTableCreateDTO timeTableDTO = new TimeTableCreateDTO(MEMBER_ID, null,
//                                                TIMETABLE_CONTENT, TIMETABLE_PERIOD_START, TIMETABLE_PERIOD_END);
//
//        Long sampleTimeTableId = 1L;
//        given(timeTableService.saveTimeTable(any())).willThrow(new NullPointerException());
//
//        // when
//        final ResultActions resultActions = mockMvc.perform(
//                MockMvcRequestBuilders.post("/timeTable/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(timeTableDTO)));
//
//        // then
//        MvcResult mvcResult = resultActions.andReturn();
//        String contentAsString = mvcResult.getResponse().getContentAsString();
//        System.out.println("contentAsString = " + contentAsString);
//        resultActions.andExpect(status().isBadRequest());
//    }

    @Test
    @DisplayName("특정 시간표 가져오기 테스트")
    public void getTimeTableTest() throws Exception {
        // given
        Long savedTimeTableId = saveTimeTable();
        TimeTableDTO savedTimeTableDTO = new TimeTableDTO(savedTimeTableId, TIMETABLE_NAME, TIMETABLE_CONTENT, TIMETABLE_PERIOD_START, TIMETABLE_PERIOD_END);
        given(timeTableService.findTimeTable(any(), any())).willReturn(savedTimeTableDTO);

        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/timeTable/" + savedTimeTableId + "/" + MEMBER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeTableId").value(savedTimeTableId))
                .andExpect(jsonPath("$.tableName").value(TIMETABLE_NAME))
                .andExpect(jsonPath("$.content").value(TIMETABLE_CONTENT));
    }

    @Test
    @DisplayName("특정 Member의 시간표 전부 가져오기 테스트")
    public void getTimeTableListTest() throws Exception {
        // given
        Long savedTimeTableId = saveTimeTable();
        TimeTableDTO savedTimeTableDTO = new TimeTableDTO(savedTimeTableId, TIMETABLE_NAME, TIMETABLE_CONTENT, TIMETABLE_PERIOD_START, TIMETABLE_PERIOD_END);
        TimeTableDTO savedTimeTableDTO2 = new TimeTableDTO(2L, TIMETABLE_NAME, TIMETABLE_CONTENT, TIMETABLE_PERIOD_START, TIMETABLE_PERIOD_END);
        TimeTableListDTO timeTableListDTO = new TimeTableListDTO();
        timeTableListDTO.addTimeTable(savedTimeTableDTO, savedTimeTableDTO2);
        given(timeTableService.findTimeTablesByMemberId(any())).willReturn(timeTableListDTO);

        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/timeTable/" + MEMBER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeTableList[0].timeTableId").value(savedTimeTableId))
                .andExpect(jsonPath("$.timeTableList[0].tableName").value(TIMETABLE_NAME))
                .andExpect(jsonPath("$.timeTableList[0].content").value(TIMETABLE_CONTENT))
                .andExpect(jsonPath("$.timeTableList[1].timeTableId").value(2L));
    }

    @Test
    @DisplayName("시간표 업데이트 테스트")
    public void updateTimeTableTest() throws Exception {
        // given
        TimeTableCreateDTO timeTableDTO = new TimeTableCreateDTO(MEMBER_ID, TIMETABLE_NAME,
                TIMETABLE_CONTENT, TIMETABLE_PERIOD_START, TIMETABLE_PERIOD_END);
        Long sampleTimeTableId = 1L;
        TimeTableIdDTO timeTableIdDTO = new TimeTableIdDTO(true, sampleTimeTableId);

        TimeTableCreateDTO timeTableDTO4Update = new TimeTableCreateDTO(MEMBER_ID, TIMETABLE_NAME,
                "새 컨텐츠 is ...", TIMETABLE_PERIOD_START, TIMETABLE_PERIOD_END);

        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders.put("/timeTable/" + sampleTimeTableId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timeTableDTO4Update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(timeTableIdDTO.isSuccess()))
                .andExpect(jsonPath("$.timeTableId").value(timeTableIdDTO.getTimeTableId()));
    }

    @Test
    @DisplayName("시간표 삭제 테스트")
    public void deleteTimeTableTest() throws Exception {
        // given
        long sampleTimeTableId = 1L;

        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/timeTable/" + MEMBER_ID + "/" + sampleTimeTableId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    private Long saveTimeTable() throws Exception {
        TimeTableCreateDTO timeTableDTO = new TimeTableCreateDTO(MEMBER_ID, TIMETABLE_NAME,
                TIMETABLE_CONTENT, TIMETABLE_PERIOD_START, TIMETABLE_PERIOD_END);
        Long sampleTimeTableId = 1L;
        when(timeTableService.createTimeTable(any())).thenReturn(sampleTimeTableId);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/timeTable/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timeTableDTO)));
        return sampleTimeTableId;
    }
}