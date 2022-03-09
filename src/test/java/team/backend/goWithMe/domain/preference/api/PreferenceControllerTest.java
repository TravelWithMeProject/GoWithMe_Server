package team.backend.goWithMe.domain.preference.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import team.backend.goWithMe.domain.preference.application.PreferenceService;
import team.backend.goWithMe.domain.preference.domain.persist.Preference;
import team.backend.goWithMe.domain.preference.dto.PreferenceCreateRequest;
import team.backend.goWithMe.domain.preference.dto.PreferenceCreateResponse;
import team.backend.goWithMe.domain.preference.dto.PreferenceFindDTO;
import team.backend.goWithMe.domain.preference.dto.PreferenceUpdateDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.*;
import static team.backend.goWithMe.domain.preference.domain.util.GivenPreference.*;


@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "ssar@naver.com", roles = "USER")
class PreferenceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PreferenceService preferenceService;

    @Autowired
    ObjectMapper objectMapper;

    static Preference givenPrefer = Preference.createSurvey(givenArrival, givenAccommodation, givenPeriod);

    @Test
    void createSurvey() throws Exception {
        PreferenceCreateRequest requestPrefer = PreferenceCreateRequest.createRequest(givenPrefer);
        PreferenceCreateResponse responsePrefer = PreferenceCreateResponse.createResponse(givenPrefer);

        String body = objectMapper.writeValueAsString(requestPrefer);

        when(preferenceService.createFavorite(any(), any())).thenReturn(responsePrefer);

        mockMvc.perform(post("/api/v1/preference/save").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void findPreference() throws Exception {
        PreferenceFindDTO findPrefer = PreferenceFindDTO.from(givenPrefer);

        when(preferenceService.findOne(any())).thenReturn(findPrefer);

        mockMvc.perform(get("/api/v1/preference")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updatePreference() throws Exception {
        PreferenceUpdateDTO updatePrefer = PreferenceUpdateDTO.createUpdate(givenPrefer);
        String body = objectMapper.writeValueAsString(updatePrefer);

        mockMvc.perform(patch("/api/v1/preference").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deletePreference() throws Exception {
        mockMvc.perform(delete("/api/v1/preference"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}