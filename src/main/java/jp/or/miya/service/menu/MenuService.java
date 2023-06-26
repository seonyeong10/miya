package jp.or.miya.service.menu;

import jakarta.servlet.http.HttpServletRequest;
import jp.or.miya.config.jwt.JwtTokenProvider;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.MenuRepository;
import jp.or.miya.web.dto.request.SearchRequestDto;
import jp.or.miya.web.dto.request.menu.MenuSaveRequestDto;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import jp.or.miya.web.dto.response.menu.MenuListResponseDto;
import jp.or.miya.web.dto.response.menu.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id = " + id));
        menu.update(id, requestDto);
        return menu.getId();
    }

    public List<MenuListResponseDto> findAll (SearchRequestDto requestDto) {
        PageRequest page = PageRequest.of(requestDto.getPage(), requestDto.getPage() + 10, Sort.by(Sort.Direction.DESC, "id"));

        return menuRepository.findAll(page).stream()
                .map(MenuListResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<MenuListResponseDto> findPart (String part, SearchRequestDto requestDto) {
        PageRequest page = PageRequest.of(requestDto.getPage(), requestDto.getPage() + 10, Sort.by(Sort.Direction.DESC, "id"));

        return menuRepository.findByPart(part, page).stream()
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
