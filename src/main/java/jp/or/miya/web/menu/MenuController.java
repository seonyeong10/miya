package jp.or.miya.web.menu;

import jakarta.servlet.http.HttpServletRequest;
import jp.or.miya.service.menu.MenuService;
import jp.or.miya.web.dto.request.SearchRequestDto;
import jp.or.miya.web.dto.request.menu.MenuSaveRequestDto;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import jp.or.miya.web.dto.response.menu.MenuListResponseDto;
import jp.or.miya.web.dto.response.menu.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService service;

    @PostMapping("/api/v1/menus")
    public ResponseEntity<?> saveWithFile (
            @RequestPart(value = "content") MenuSaveRequestDto requestDto,
            @RequestPart(value = "file") List<MultipartFile> files
    ) {
        return service.saveWithFile(requestDto, files);
    }

    @GetMapping("/api/v1/menus") // GET 은 RequestBody가 없음
    public Page<MenuListResponseDto> findAll (
            SearchRequestDto requestDto
    ) {
        return service.findAll(requestDto);
    }

    @GetMapping("/api/v1/menus/{parentCategoryId}")
    public Page<MenuListResponseDto> findPart (
            @PathVariable(value = "parentCategoryId") Long parentCategoryId,
            SearchRequestDto requestDto
    ) {
        requestDto.setParentCategoryId(parentCategoryId);
        return service.findAll(requestDto);
    }

    @GetMapping("/api/menus/{part}/{id}")
    public MenuResponseDto findOne (
            @PathVariable(value = "id") Long id
    ) {
        return service.findOne(id);
    }

    @PostMapping("/api/v1/menus/{part}/{id}")
    public ResponseEntity<?> updateWithFile (
            @PathVariable(value = "id") Long id,
            @RequestPart(value = "content") MenuUpdateRequestDto requestDto,
            @RequestPart(value = "file") List<MultipartFile> files
    ) {
        return service.updateWithFile(id, requestDto, files);
    }

    @DeleteMapping("/api/menus/{part}/{id}")
    public Long delete (
            @PathVariable(value = "id") Long id
    ) {
        service.delete(id);
        return id;
    }
}
