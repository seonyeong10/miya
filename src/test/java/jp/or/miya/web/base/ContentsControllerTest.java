package jp.or.miya.web.base;

import jp.or.miya.domain.base.Contents;
import jp.or.miya.domain.base.repository.ContentsRepository;
import jp.or.miya.domain.base.ContentsSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContentsControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ContentsRepository repository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void get_roleContent() throws Exception {
        // given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMzA2MTIwMDEiLCJhdXRoIjoiUk9MRV9BRE1JTiIsIm5hbWUiOiLlrq7msrsiLCJleHAiOjE2ODc0OTMyOTF9.Ho-8eRj3rx9DB_l1741o97xfjZvcEoyAlu6ne5fIFmM";
        String url = "http://localhost:" + port + "/api/adm/contents";
        String role = "ROLE_ADMIN";
        String sort = "MENU";

        // when
        mvc.perform(get(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("ROLE_ADMIN"));

        // then
        Sort order = Sort.by(Sort.Order.asc("id"), Sort.Order.asc("seq"));
        List<Contents> contents = repository.findAll(ContentsSpecification.equalsSortNRole(sort, role), order);
        assertThat(contents.get(0).getId()).isEqualTo(1);
        assertThat(contents.get(0).getName()).isEqualTo("View All");
        assertThat(contents.get(0).getUrl()).isEqualTo("/menus");
    }
}
