package jp.or.miya.web.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.file.AttachFileRepository;
import jp.or.miya.web.dto.request.AttachFileRequestDto;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerTest {
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
    private AttachFileRepository fileRepository;

    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown () {
//        fileRepository.deleteAll();
    }

    @DisplayName("POST /files/{parentId} 첨부파일 업로드 테스트")
    @Test
    public void file_upload () throws Exception {
        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("file", "hello2.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        String parentId = "62";
        String dir = "/menus/drinks";

        String url = "http://localhost:" + port + "/api/files/" + parentId;

        mvc.perform(multipart(url)
                .file(multipartFile1)
                .file(multipartFile2)
                .param("dir", dir)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk());

        List<AttachFile> all = fileRepository.findAll();
        assertThat(all.get(0).getDir()).isEqualTo(dir);
        assertThat(all.get(0).getOrgName()).isEqualTo("hello.txt");
    }

    @DisplayName("DELETE /files/{parentId} 첨부파일 삭제 테스트")
    @Test
    public void file_delete () throws Exception {
        //given
        AttachFileRequestDto.Delete requestDto = AttachFileRequestDto.Delete.builder()
                .remove(new ArrayList<>(Arrays.asList(11L)))
                .build();

        String url = "http://localhost:" + port + "/api/files/" + 62;

        //when
        mvc.perform(delete(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());

        //then
    }
}
