package jp.or.miya.web.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.file.AttachFileRepository;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.MenuRepository;
import jp.or.miya.domain.menu.Nutrient;
import jp.or.miya.web.dto.request.SearchRequestDto;
import jp.or.miya.web.dto.request.menu.MenuSaveRequestDto;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    @Autowired
    private AttachFileRepository fileRepository;

    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @AfterEach()
    public void tearDown() throws Exception {
        menuRepository.deleteAll();
    }

    @DisplayName("POST /api/menus 메뉴 저장 테스트")
    @Test
    public void menu_save () throws Exception {
        //given
        String part = "FOODS";
        String category = "Onigiri";
        String name = "테스트";
        String engName = "test";
        LocalDateTime saleStartDt = LocalDateTime.now();
        LocalDateTime saleEndDt = saleStartDt.plusDays(20);
        Long price = 300L;

        Long calorie = 1000L;

        MenuSaveRequestDto requestDto = MenuSaveRequestDto.builder()
                .part(part)
                .category(category)
                .name(name)
                .engName(engName)
                .saleStartDt(saleStartDt)
                .saleEndDt(saleEndDt)
                .price(price)
                .calorie(calorie)
                .build();

        String url = "http://localhost" + port + "/api/menus";

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
        assertThat(all.get(0).getModEmp()).isEqualTo(230612001L);
        assertThat(all.get(0).getNutrient().getCalorie()).isEqualTo(calorie);
    }

    @DisplayName("POST /api/v1/menus 메뉴 저장 with File 테스트")
    @Test
//    @Transactional // org.hibernate.LazyInitializationException
    public void menu_saveWithFile () throws Exception {
        //given
        String part = "FOODS";
        String category = "Onigiri";
        String name = "테스트";
        String engName = "test";
        LocalDateTime saleStartDt = LocalDateTime.now();
        LocalDateTime saleEndDt = saleStartDt.plusDays(20);
        Long price = 300L;
        Long calorie = 1000L;

        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("file", "hello2.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        String dir = "/menus/drinks";

        MenuSaveRequestDto requestDto = MenuSaveRequestDto.builder()
                .part(part)
                .category(category)
                .name(name)
                .engName(engName)
                .saleStartDt(saleStartDt)
                .saleEndDt(saleEndDt)
                .price(price)
                .calorie(calorie)
                .dir(dir)
                .build();

        MockMultipartFile content = new MockMultipartFile("content", "content", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(requestDto).getBytes());

        String url = "http://localhost:" + port + "/api/v1/menus";

        //when
        mvc.perform(multipart(url)
                .file(multipartFile1)
                .file(multipartFile2)
                .file(content)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        List<Menu> all = menuRepository.findAll();
        assertThat(all.get(0).getPart()).isEqualTo(part);
        assertThat(all.get(0).getCategory()).isEqualTo(category);
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getEngName()).isEqualTo(engName);
        assertThat(all.get(0).getNutrient().getCalorie()).isEqualTo(calorie);
        assertThat(all.get(0).getAttachFiles().size()).isEqualTo(2);
        System.out.println("menu_id = " + all.get(0).getNutrient().getMenu());
    }

    @DisplayName("PUT /api/v1/menus/{category}/{id} 메뉴 수정")
    @Test
//    @Transactional
    public void menu_updateWithFile () throws Exception {
        // given
        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("file", "hello2.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        String dir = "/menus/drinks";

        Menu savedMenu = menuRepository.save(Menu.builder()
                        .name("테스트")
                        .engName("Test")
                        .part("MENUS")
//                        .nutrient(Nutrient.builder().calorie(100L).build())
                .build());

        AttachFile attachFile = fileRepository.save(AttachFile.builder()
                .menu(savedMenu)
                .orgName("Test.txt")
                .name("Test.txt")
                .build());

        Long updatedId = savedMenu.getId();
        String expectedName = "테스트2";
        String expectedEngName = "Test2";
//        Long expectedCalorie = 200L;
        List<Long> remove = new ArrayList<>(Arrays.asList(attachFile.getId()));
        MenuUpdateRequestDto requestDto = MenuUpdateRequestDto.builder()
                .name(expectedName)
                .engName(expectedEngName)
//                .calorie(expectedCalorie)
                .dir(dir)
                .remove(remove)
                .build();

        MockMultipartFile content = new MockMultipartFile("content", "content", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(requestDto).getBytes());
        String url = "http://localhost:" + port + "/api/v1/menus/Onigiri/" + updatedId;

        // when
        mvc.perform(multipart(url)
                .file(multipartFile1)
                .file(multipartFile2)
                .file(content)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isOk());

        // then
        Menu menu = menuRepository.findById(updatedId).orElseThrow(() -> new IllegalArgumentException("조회 데이터가 없습니다. id = " + updatedId));
        assertThat(menu.getName()).isEqualTo(expectedName);
        assertThat(menu.getEngName()).isEqualTo(expectedEngName);
        assertThat(menu.getAttachFiles().size()).isEqualTo(2);
//        assertThat(menu.getNutrient().getCalorie()).isEqualTo(expectedCalorie);
    }

    @DisplayName("GET /api/v1/menus 메뉴 전체 조회")
    @Test
    public void menu_findAll () throws Exception {
        //given
        String name = "name";
        String category = "Drinks";

        menuRepository.save(Menu.builder()
                .name(name + 1)
                .engName("english1")
                .part("FOODS")
                .category("Drinks")
                .nutrient(Nutrient.builder().calorie(100L).build())
                .build());

        SearchRequestDto find = SearchRequestDto.builder()
                .page(0)
                .keyword(name)
                .category(new ArrayList<>(Arrays.asList(category)))
                .build();

        String url = "http://localhost:" + port + "/api/v1/menus";

        //when
        mvc.perform(get(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(find)))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        List<Menu> all = menuRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name+1);
        assertThat(all.get(0).getCategory()).isEqualTo(category);
    }

    @DisplayName("GET /api/menus/{part} 분류별 메뉴 조회")
    @Test
    public void menu_findPart () throws Exception {
        //given
        String part = "foods";
        int page = 0;
        SearchRequestDto find = SearchRequestDto.builder()
                .page(page)
                .build();

        String url = "http://localhost:" + port + "/api/menus/" + part;

        //when
        mvc.perform(get(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(find)))
                .andExpect(status().isOk());

        //then
    }

    @DisplayName("GET /api/menus/{part}/{id} 메뉴 조회")
    @Test
    public void menu_findOne () throws Exception {
        // given
        Menu savedMenu = menuRepository.save(Menu.builder()
                .name("name1")
                .engName("english1")
                .part("MENUS")
                .nutrient(Nutrient.builder().menuId(60L).calorie(100L).build())
                .build());

        Long savedId = savedMenu.getId();
        String url = "http://localhost:" + port + "/api/menus/foods/" + savedId;

        // when
        mvc.perform(get(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        // then
    }
}
