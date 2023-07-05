package jp.or.miya.domain.menu.repository;

import jp.or.miya.domain.menu.Menu;
import jp.or.miya.web.dto.request.SearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuRepositoryCustom {
    // queryDsl 로 구현할 메소드 생성
    Page<Menu> findAllComplex(SearchRequestDto dto, Pageable pageable); // 검색 조회
}
