package jp.or.miya.web.menu;

import jp.or.miya.domain.menu.Menu;
import jp.or.miya.service.menu.MenuService;
import jp.or.miya.web.dto.request.MenuRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<Page<Menu>> findAll () {
        return service.findAll();
    }
}
