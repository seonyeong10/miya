package jp.or.miya.web.menu;

import jp.or.miya.service.menu.MenuService;
import jp.or.miya.web.dto.request.MenuRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
