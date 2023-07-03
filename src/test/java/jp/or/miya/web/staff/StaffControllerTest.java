package jp.or.miya.web.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.or.miya.domain.staff.*;
import jp.or.miya.domain.user.Role;
import jp.or.miya.web.dto.request.staff.StaffSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StaffControllerTest {
    @LocalServerPort
    private int port;
    @Value("${jwt.test.accessToken}")
    private String token;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private StaffRepository staffRepository;

    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown () throws Exception {}

    @DisplayName("POST /api/v1/staff 직원 등록 테스트")
    @Test
    public void staff_save () throws Exception {
        //given
        String name = "테스트";
        String engName = "Test";
        Position pos = Position.ASSISTANT;
        Responsibility res = Responsibility.NONE;
        Work work = Work.WORK;
        Role role = Role.USER;
        LocalDate joinDt = LocalDate.now();
        String ext = "031)222-1234";

        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        StaffSaveRequestDto requestDto = StaffSaveRequestDto.builder()
                .name(name)
                .engName(engName)
                .work(work)
                .role(role)
                .pos(pos)
                .res(res)
                .joinDt(joinDt)
                .ext(ext)
                .build();

        MockMultipartFile content = new MockMultipartFile("content", "content", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(requestDto).getBytes());

        String url = "http://localhost:" + port + "/api/v1/staff";

        //when
        mvc.perform(multipart(url)
                .file(multipartFile1)
                .file(content)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        Optional<Staff> staff = staffRepository.findById(8L);
        assertThat(staff.get().getWork()).isEqualTo(work);
    }
}
