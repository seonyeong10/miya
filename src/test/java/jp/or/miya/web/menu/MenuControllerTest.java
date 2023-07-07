package jp.or.miya.web.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.or.miya.domain.Period;
import jp.or.miya.domain.base.Category;
import jp.or.miya.domain.base.repository.CategoryRepository;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.file.repository.AttachFileRepository;
import jp.or.miya.domain.item.Menu;
import jp.or.miya.domain.item.repository.MenuRepository;
import jp.or.miya.domain.item.Nutrient;
import jp.or.miya.domain.item.repository.NutrientRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
    @Autowired
    private NutrientRepository nutrientRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @AfterEach()
    public void tearDown() throws Exception {
//        menuRepository.deleteAll();
    }

    @DisplayName("POST /api/v1/menus 메뉴 저장 with File 테스트")
    @Test
    public void menu_saveWithFile () throws Exception {
        //given
        String name = "테스트";
        String engName = "test";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(20);
        int price = 300;
        Long calorie = 1000L;

        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("file", "hello2.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        String dir = "/test";

        // 카테고리 저장
        Category parent = categoryRepository.saveAndFlush(Category.builder().name("Foods").build());
        Category category = categoryRepository.saveAndFlush(Category.builder().parent(parent).name("Onigiri").build());

        MenuSaveRequestDto requestDto = MenuSaveRequestDto.builder()
                .categoryId(category.getId())
                .name(name)
                .engName(engName)
                .startDate(startDate)
                .endDate(endDate)
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
        assertThat(all.get(0).getCategory().getName()).isEqualTo(category.getName());
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getEngName()).isEqualTo(engName);
    }

    @DisplayName("PUT /api/v1/menus/{category}/{id} 메뉴 수정")
    @Test
    public void menu_updateWithFile () throws Exception {
        // given
        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("file", "hello2.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        String dir = "/test";

        // 카테고리
        Category parent = categoryRepository.saveAndFlush(Category.builder().name("Foods").build());
        Category category = categoryRepository.saveAndFlush(Category.builder().parent(parent).name("Onigiri").build());
        // 메뉴
        Menu savedMenu = menuRepository.saveAndFlush(Menu.builder()
                        .name("테스트")
                        .engName("Test")
                .build());
        // 영양정보
        Nutrient savedNutrient = nutrientRepository.saveAndFlush(Nutrient.builder()
                        .menu(savedMenu)
                        .calorie(100L)
                        .fat(10)
                .build());
        // 삭제할 파일
        AttachFile attachFile = fileRepository.saveAndFlush(AttachFile.builder()
                        .item(savedMenu)
                        .orgName("Test.txt")
                        .name("Test.txt")
                        .dir(dir)
                .build());

        Long updatedId = savedMenu.getId();
        String expectedName = "테스트2";
        String expectedEngName = "Test2";
        Long expectedCalorie = 200L;
        List<Long> remove = new ArrayList<>(Arrays.asList(attachFile.getId()));
        MenuUpdateRequestDto requestDto = MenuUpdateRequestDto.builder()
                .name(expectedName)
                .engName(expectedEngName)
                .calorie(expectedCalorie)
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
        assertThat(menu.getNutrient().getCalorie()).isEqualTo(expectedCalorie);
    }

    @DisplayName("GET /api/v1/menus 메뉴 전체 조회")
    @Test
    public void menu_findAll () throws Exception {
        //given
        String name = "name";
        Category parent = categoryRepository.saveAndFlush(Category.builder().name("Foods").build());
        Category onigiri = categoryRepository.saveAndFlush(Category.builder().parent(parent).name("Onigiri").build());
        Category udon = categoryRepository.saveAndFlush(Category.builder().parent(parent).name("Udon").build());

        LocalDateTime startDate = LocalDateTime.now();
        Menu savedMenu = menuRepository.saveAndFlush(Menu.builder()
                .name(name + 1)
                .engName("english1")
                .category(onigiri)
                .period(Period.builder().startDate(startDate).build())
                .build());

        List<Long> list = new ArrayList<>(Arrays.asList(onigiri.getId(), udon.getId()));
        SearchRequestDto find = SearchRequestDto.builder()
                .page(0)
                .keyword(name)
                .categoryIds(list)
                .build();

        String url = "http://localhost:" + port + "/api/v1/menus";

        //when
        mvc.perform(get(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("keyword", name)
                .param("categoryIds", onigiri.getId().toString())
                .param("categoryIds", udon.getId().toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("GET /api/menus/{part}/{id} 메뉴 조회")
    @Test
    public void menu_findOne () throws Exception {
        // given
        Menu savedMenu = menuRepository.saveAndFlush(Menu.builder()
                .name("name1")
                .engName("english1")
                .period(Period.builder().startDate(LocalDateTime.now()).build())
                .build());

        Long savedId = savedMenu.getId();

        String url = "http://localhost:" + port + "/api/menus/foods/" + savedId;

        // when
        mvc.perform(get(url)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("테이블 생성 테스트")
    @Test
    public void table_test () throws Exception {
        System.out.println("fin");
//        menuRepository.findAllComplex(new SearchRequestDto(), PageRequest.of(0, 10));
    }
}
