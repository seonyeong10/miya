package jp.or.miya.service.menu;

import jp.or.miya.domain.menu.MenuRepository;
import jp.or.miya.web.dto.request.MenuRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Transactional
    public Long save(MenuRequestDto.Save saveDto) {
        return menuRepository.save(saveDto.toEntity()).getId();
    }
}
