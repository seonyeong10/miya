package jp.or.miya.service.menu;

import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.MenuRepository;
import jp.or.miya.web.dto.request.MenuRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Transactional
    public Long save(MenuRequestDto.Save saveDto) {
        return menuRepository.save(saveDto.toEntity()).getId();
    }

    public ResponseEntity<Page<Menu>> findAll (MenuRequestDto.Find find) {
        PageRequest page = PageRequest.of(find.getPage(), find.getPage() + 10);

        // QueryDSL

        return ResponseEntity.ok(menuRepository.findAll(page));
    }

    public ResponseEntity<Page<Menu>> findPart (String part, MenuRequestDto.Find find) {
        return ResponseEntity.ok(
                menuRepository.findByPart(part, PageRequest.of(find.getPage(), find.getPage() + 10))
        );
    }
}
