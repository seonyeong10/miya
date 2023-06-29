package jp.or.miya.web.menu;

import jakarta.servlet.http.HttpServletRequest;
import jp.or.miya.service.menu.MenuService;
import jp.or.miya.web.dto.request.SearchRequestDto;
import jp.or.miya.web.dto.request.menu.MenuSaveRequestDto;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import jp.or.miya.web.dto.response.menu.MenuListResponseDto;
import jp.or.miya.web.dto.response.menu.MenuResponseDto;
import lombok.RequiredArgsConstructor;
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
            @RequestPart(value = "file") List<MultipartFile> files,
            HttpServletRequest request
    ) {
        return service.saveWithFile(requestDto, files, request);
    }

    @GetMapping("/api/menus") // GET 은 RequestBody가 없음
    public List<MenuListResponseDto> findAll (
            SearchRequestDto requestDto
    ) {
        return service.findAll(requestDto);
    }

    @GetMapping("/api/menus/{part}")
    public List<MenuListResponseDto> findPart (
            @PathVariable(value = "part") String part,
            SearchRequestDto requestDto
    ) {
        return service.findPart(part, requestDto);
    }

    @GetMapping("/api/menus/{part}/{id}")
    public MenuResponseDto findOne (
            @PathVariable(value = "id") Long id
    ) {
        return service.findOne(id);
    }

//    @PutMapping("/api/v1/menus/{part}/{id}")
    @PostMapping("/api/v1/menus/{part}/{id}")
    public ResponseEntity<?> updateWithFile (
            @PathVariable(value = "id") Long id,
//            @RequestBody MenuUpdateRequestDto requestDto,
            @RequestPart(value = "content") MenuUpdateRequestDto requestDto,
            @RequestPart(value = "file") List<MultipartFile> files,
            HttpServletRequest request
    ) {
        return service.updateWithFile(id, requestDto, files, request);
    }

    @DeleteMapping("/api/menus/{part}/{id}")
    public Long delete (
            @PathVariable(value = "id") Long id
    ) {
        service.delete(id);
        return id;
    }
}
