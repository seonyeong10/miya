package jp.or.miya.service.menu;

import jakarta.servlet.http.HttpServletRequest;
import jp.or.miya.config.jwt.JwtTokenProvider;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.MenuRepository;
import jp.or.miya.domain.menu.NutrientRepository;
import jp.or.miya.web.dto.request.MenuRequestDto;
import jp.or.miya.web.dto.request.menu.MenuSaveRequestDto;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long save(MenuSaveRequestDto requestDto, HttpServletRequest request) {
        // 수정자 등록
        requestDto.setModEmp(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)));
        Menu menu = menuRepository.save(requestDto.toEntity());
        menu.getNutrient().setMenuId(menu.getId());
        return menu.getId();
    }

    @Transactional
    public Long update (Long id, MenuUpdateRequestDto requestDto) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        menu.update(id, requestDto);
        return menu.getId();
    }

    public ResponseEntity<Page<Menu>> findAll (MenuRequestDto.Find find) {
        PageRequest page = PageRequest.of(find.getPage(), find.getPage() + 10);
        return ResponseEntity.ok(menuRepository.findAll(page));
    }

    public ResponseEntity<Page<Menu>> findPart (String part, MenuRequestDto.Find find) {
        return ResponseEntity.ok(
                menuRepository.findByPart(part, PageRequest.of(find.getPage(), find.getPage() + 10))
        );
    }

    public ResponseEntity<Menu> findOne (Long menuId) {
        return ResponseEntity.of(menuRepository.findById(menuId));
    }
}
