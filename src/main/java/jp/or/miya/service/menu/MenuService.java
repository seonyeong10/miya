package jp.or.miya.service.menu;

import jp.or.miya.config.jwt.JwtTokenProvider;
import jp.or.miya.domain.base.Category;
import jp.or.miya.domain.base.repository.CategoryRepository;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.file.repository.AttachFileRepository;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.repository.MenuRepository;
import jp.or.miya.domain.menu.Nutrient;
import jp.or.miya.domain.menu.repository.MenuRepositoryImpl;
import jp.or.miya.domain.menu.repository.NutrientRepository;
import jp.or.miya.lib.FileUtils;
import jp.or.miya.web.dto.request.SearchRequestDto;
import jp.or.miya.web.dto.request.menu.MenuSaveRequestDto;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import jp.or.miya.web.dto.response.menu.MenuListResponseDto;
import jp.or.miya.web.dto.response.menu.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final String BASE_DIR = "D:/03. Project/07. Miya/uploads";
    private final MenuRepository menuRepository; // JPA
    private final NutrientRepository nutrientRepository;
    private final AttachFileRepository fileRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtils fileUtils;

    @Transactional
    public ResponseEntity<?> saveWithFile (MenuSaveRequestDto requestDto, List<MultipartFile> files) {
        // 카테고리 조회
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new NoSuchElementException("해당 카테고리가 존재하지 않습니다. category_id = " + requestDto.getCategoryId()));

        // 첨부파일 저장
        Set<AttachFile> attachFiles = new HashSet<>();
        try {
            fileUtils.saveFiles(attachFiles, files, requestDto.getDir());
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
        }

        // 메뉴 저장
        Menu menu = menuRepository.save(requestDto.toMenuEntity()); // insert
        menu.addCategory(category); // 카테고리 저장
        // 영양정보 저장
        Nutrient nutrient = requestDto.toNutrientEntity(menu);
        nutrientRepository.save(nutrient); // insert
        // 첨부파일 저장
        attachFiles.stream().forEach(file -> file.addMenu(menu)); // 메뉴 등록
        fileRepository.saveAll(attachFiles); // insert

        return ResponseEntity.ok(menu.getId());
    }

    @Transactional
    public ResponseEntity<?> updateWithFile (Long id, MenuUpdateRequestDto requestDto, List<MultipartFile> files) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id = " + id));
        // (UnsupportedOperationException) List.copyOf 로 생성된 Set 은 수정할 수 없다.
        Set<AttachFile> attachFiles = menu.getAttachFiles(); // 기존 첨부파일
        Nutrient nutrient = menu.getNutrient(); // 기존 영양정보

        // 파일 삭제
        try {
            fileUtils.deleteFile(attachFiles, requestDto.getRemove());
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 삭제 실패", HttpStatus.BAD_REQUEST);
        }


        // 신규 파일 저장
        try {
            fileUtils.saveFiles(attachFiles, files, requestDto.getDir());
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
        }

        // Update
        menu.update(requestDto);
        nutrient.update(requestDto);

        return ResponseEntity.ok(menu.getId());
    }

    public List<MenuListResponseDto> findAll (SearchRequestDto requestDto) {
        PageRequest page = PageRequest.of(requestDto.getPage(), requestDto.getPage() + 10, Sort.by(Sort.Direction.DESC, "id"));

        Page<Menu> results = menuRepository.findAllComplex(requestDto, page);

        return results.getContent().stream()
                .map(MenuListResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<MenuListResponseDto> findPart (String part, SearchRequestDto requestDto) {
        PageRequest page = PageRequest.of(requestDto.getPage(), requestDto.getPage() + 10, Sort.by(Sort.Direction.DESC, "id"));

        return menuRepository.findByPartOrderByIdDesc(part, page).stream()
                .map(MenuListResponseDto::new)
                .collect(Collectors.toList());
    }

    public MenuResponseDto findOne (Long id) {
        Menu entity = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id = " + id));

        return new MenuResponseDto(entity);
    }

    public void delete (Long id) {
        Menu entity = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id = " + id));
        menuRepository.delete(entity);
    }
}
