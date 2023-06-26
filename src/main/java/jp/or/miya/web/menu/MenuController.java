package jp.or.miya.web.menu;

import jakarta.servlet.http.HttpServletRequest;
import jp.or.miya.config.jwt.JwtTokenProvider;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.service.menu.MenuService;
import jp.or.miya.web.dto.request.MenuRequestDto;
import jp.or.miya.web.dto.request.menu.MenuSaveRequestDto;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService service;

    @PostMapping("/api/menus")
    public Long save(
            @RequestBody MenuSaveRequestDto requestDto,
            HttpServletRequest request
    ) {
        return service.save(requestDto, request);
    }

    @GetMapping("/api/menus")
    public ResponseEntity<Page<Menu>> findAll (
            MenuRequestDto.Find find // GET 은 RequestBody가 없음
    ) {
        return service.findAll(find);
    }

    @GetMapping("/api/menus/{part}")
    public ResponseEntity<Page<Menu>> findPart (
            @PathVariable(value = "part") String part,
            MenuRequestDto.Find find
    ) {
        return service.findPart(part, find);
    }

    @GetMapping("/api/menus/{part}/{id}")
    public ResponseEntity<Menu> findOne (
            @PathVariable(value = "id") Long id
    ) {
        return service.findOne(id);
    }

    @PutMapping("/api/menus/{part}/{id}")
    public Long update (
            @PathVariable(value = "id") Long id,
            @RequestBody MenuUpdateRequestDto requestDto,
            HttpServletRequest request
    ) {
        return service.update(id, requestDto);
    }
}
