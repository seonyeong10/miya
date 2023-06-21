package jp.or.miya.web.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.MenuRepository;
import jp.or.miya.web.dto.request.MenuRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuControllerTest {
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
    private MenuRepository menuRepository;

    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @AfterEach
    public void tearDown() throws Exception {
        menuRepository.deleteAll();
    }

    @Test
    public void menu_save () throws Exception {
        //given
        String part = "foods";
        String category = "Onigiry";
        String name = "테스트";
        String engName = "test";
        LocalDateTime saleStartDt = LocalDateTime.now();
        LocalDateTime saleEndDt = LocalDateTime.parse("2023-07-01T23:59:59.999");
        Long price = 300L;

        MenuRequestDto.Save requestDto = MenuRequestDto.Save.builder()
                .part(part)
                .category(category)
                .name(name)
                .engName(engName)
                .saleStartDt(saleStartDt)
                .saleEndDt(saleEndDt)
                .price(price)
                .build();

        String url = "http://localhost" + port + "/api/menus";
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMzA2MTIwMDEiLCJhdXRoIjoiUk9MRV9BRE1JTiIsIm5hbWUiOiLlrq7msrsiLCJleHAiOjE3MTg4NDQ0MDF9.1PvpnbJ1e4UOFnOe2E4-cE1JGSgTP06H1OCcHbFnyaM";

        //when
        mvc.perform(post(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Menu> all = menuRepository.findAll();
        assertThat(all.get(0).getCategory()).isEqualTo(category);
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getPrice()).isEqualTo(price);
    }
}
