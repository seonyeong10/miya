package jp.or.miya.service.menu;

import jp.or.miya.domain.base.Category;
import jp.or.miya.domain.base.repository.CategoryRepository;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.file.repository.AttachFileRepository;
import jp.or.miya.domain.item.Item;
import jp.or.miya.domain.item.Menu;
import jp.or.miya.domain.item.repository.MenuRepository;
import jp.or.miya.domain.item.Nutrient;
import jp.or.miya.domain.item.repository.NutrientRepository;
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
import java.util.*;
import java.util.stream.Collectors;

import static jp.or.miya.lib.FileUtils.deleteFile;
import static jp.or.miya.lib.FileUtils.saveFiles;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository; // JPA
    private final NutrientRepository nutrientRepository;
    private final AttachFileRepository fileRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ResponseEntity<?> saveWithFile (MenuSaveRequestDto requestDto, List<MultipartFile> files) {
        List<AttachFile> attachFiles = new ArrayList<>(); // 첨부파일

        // 카테고리 조회
        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElseThrow(() -> new NoSuchElementException("해당 카테고리가 존재하지 않습니다. category_id = " + requestDto.getCategoryId()));

        // 저장
        Menu menu = menuRepository.save(requestDto.toMenuEntity()); // insert
        menu.addCategory(category); // 카테고리 저장
        menu.addNutrient(requestDto.toNutrientEntity(menu)); // 영양정보 저장
        // 첨부파일 저장
        try {
            attachFiles = saveFiles(files, requestDto.getDir());
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
        }
        attachFiles.forEach(file -> file.addItem(menu)); // 메뉴 등록
        fileRepository.saveAll(attachFiles); // insert

        return ResponseEntity.ok(menu.getId());
    }

    @Transactional
    public ResponseEntity<?> updateWithFile (Long id, MenuUpdateRequestDto requestDto, List<MultipartFile> files) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id = " + id));
        List<AttachFile> attachFiles = menu.getAttachFiles();
        Nutrient nutrient = menu.getNutrient(); // 기존 영양정보

        // 파일 삭제
        try {
            attachFiles.removeAll(deleteFile(attachFiles, requestDto.getRemove()));
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 삭제 실패", HttpStatus.BAD_REQUEST);
        }

        // 신규 파일 저장
        try {
            attachFiles.addAll(saveFiles(files, "/staff"));
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
        }

        // Update
        menu.update(requestDto);
        nutrient.update(requestDto);

        return ResponseEntity.ok(menu.getId());
    }

    public Page<MenuListResponseDto> findAll (SearchRequestDto requestDto) {
        PageRequest page = PageRequest.of(requestDto.getPage(), requestDto.getPage() + 10, Sort.by(Sort.Direction.DESC, "id"));

        /**
         * getNumberOfElements() : 전체 Element 개수
         * getSize() : 페이지 사이즈
         */
        return menuRepository.findAllComplex(requestDto, page)
                .map(MenuListResponseDto::new);
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
