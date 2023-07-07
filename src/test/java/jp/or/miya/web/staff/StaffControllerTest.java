package jp.or.miya.web.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.file.repository.AttachFileRepository;
import jp.or.miya.domain.staff.*;
import jp.or.miya.domain.staff.enums.Position;
import jp.or.miya.domain.staff.enums.Responsibility;
import jp.or.miya.domain.staff.enums.Work;
import jp.or.miya.domain.staff.repository.StaffRepository;
import jp.or.miya.domain.user.enums.Role;
import jp.or.miya.web.dto.request.staff.StaffSaveRequestDto;
import jp.or.miya.web.dto.request.staff.StaffUpdateRequestDto;
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


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

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
    @Autowired private AttachFileRepository fileRepository;

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
        LocalDateTime startDate = LocalDateTime.now();
        String ext = "031)222-1234";

        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        StaffSaveRequestDto requestDto = StaffSaveRequestDto.builder()
                .name(name)
                .engName(engName)
                .work(work)
                .role(role)
                .pos(pos)
                .res(res)
                .startDate(startDate)
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
        List<Staff> staff = staffRepository.findAll();
        assertThat(staff.get(0).getName()).isEqualTo(name);
        assertThat(staff.get(0).getWork()).isEqualTo(work);
        assertThat(staff.get(0).getRole()).isEqualTo(role);
    }

    @DisplayName("POST /api/v1/staff/{staff_id} 직원 수정 테스트")
    @Test
    public void staff_update () throws Exception {
        //given
        String expectedName = "테스트1";
        String expectedEngName = "Test1";
        Position expectedPos = Position.CHAIRMAN;
        Responsibility expectedRes = Responsibility.CEO;
        Work expectedWork = Work.RESIGN;
        Role expectedRole = Role.ADMIN;
        String expectedExt = "02)333-5678";
        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());

        Staff savedSatff = staffRepository.saveAndFlush(Staff.builder()
                        .id("230700002")
                        .name("테스트")
                        .engName("TEST")
                        .pos(Position.ASSISTANT)
                        .res(Responsibility.NONE)
                        .work(Work.WORK)
                        .role(Role.USER)
                        .ext("031)111-1234")
                        .startDate(LocalDateTime.now())
                        .build());
        AttachFile savedFile = AttachFile.builder()
                .seq(0)
                .dir("/test")
                .orgName("ttt.txt")
                .name("test.txt")
                .build();
        savedFile.addStaff(savedSatff);
        fileRepository.saveAndFlush(savedFile);
        String savedId = savedSatff.getId();

        StaffUpdateRequestDto requestDto = StaffUpdateRequestDto.builder()
                .pw("1111")
                .res(expectedRes)
                .pos(expectedPos)
                .name(expectedName)
                .engName(expectedEngName)
                .ext(expectedExt)
                .work(expectedWork)
                .role(expectedRole)
                .remove(Arrays.asList(savedFile.getId()))
                .build();

        String url = "http://localhost:" + port + "/api/v1/staff/" + savedId;
        MockMultipartFile content = new MockMultipartFile("content", "content", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(requestDto).getBytes());

        //when
        mvc.perform(multipart(url)
                        .file(multipartFile1)
                        .file(content)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                        .andDo(print())
                        .andExpect(status().isOk());

        //then
        Staff staff = staffRepository.findById(savedId).orElseThrow(() -> new NoSuchElementException("해당 직원이 없습니다. staff_id = " + savedId));
        assertThat(staff.getName()).isEqualTo(expectedName);
        assertThat(staff.getExt()).isEqualTo(expectedExt);
        assertThat(staff.getEngName()).isEqualTo(expectedEngName);
        assertThat(staff.getPos()).isEqualTo(expectedPos);
        assertThat(staff.getRole()).isEqualTo(expectedRole);
        assertThat(staff.getRes()).isEqualTo(expectedRes);
        assertThat(staff.getWork()).isEqualTo(expectedWork);
    }

    @DisplayName("테스트")
    @Test
    public void test () throws Exception {
        System.out.println("test");
    }
}
