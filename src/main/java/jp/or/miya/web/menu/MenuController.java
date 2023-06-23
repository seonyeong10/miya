package jp.or.miya.web.menu;

import jp.or.miya.domain.menu.Menu;
import jp.or.miya.service.menu.MenuService;
import jp.or.miya.web.dto.request.MenuRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService service;

    @PostMapping("/api/menus")
    public Long save(
            @RequestBody MenuRequestDto.Save saveDto
            ) {
        return service.save(saveDto);
    }

    @GetMapping("/api/menus")
    public ResponseEntity<Page<Menu>> findAll (
            MenuRequestDto.Find find // GET 은 RequestBody가 없음
    ) {
        System.out.println(find.getPage());
        return service.findAll(find);
    }

    @GetMapping("/api/menus/{part}")
    public ResponseEntity<Page<Menu>> findPart (
            @PathVariable(value = "part") String part,
            MenuRequestDto.Find find
    ) {
        return service.findPart(part, find);
    }

    @GetMapping("/api/menus/{category}/{menuId}")
    public ResponseEntity<Menu> findOne (
            @PathVariable(value = "menuId") String menuId
    ) {
        return service.findOne(menuId);
    }
}
