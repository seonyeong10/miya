package jp.or.miya.domain.menu.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.web.dto.request.SearchRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static jp.or.miya.domain.menu.QMenu.menu;
import static jp.or.miya.domain.menu.QNutrient.nutrient;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Menu> findAllComplex(SearchRequestDto dto, Pageable pageable) {
        List<Menu> content = queryFactory
                .selectFrom(menu)
                .leftJoin(menu.nutrient, nutrient)
                .where(likeMenuName(dto), inCategory(dto))
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .fetch();

        /**
         * count 쿼리 생략
         * 1. 페이지 시작이면서 컨텐츠 사이즈가 페이지 사이즈보다 작을 때
         * 2. 마지막 페이지일 때(total size = offset + contents.size())
         */
        Long count = queryFactory.select(menu.count())
                .from(menu)
                .where(likeMenuName(dto), inCategory(dto))
                .fetchOne();


        return new PageImpl<>(content, pageable, count);
//        return content;
    }

    public BooleanExpression likeMenuName (SearchRequestDto dto) {
        // like : str 자체 비교
        // contains : %str%
        return hasText(dto.getKeyword()) ? menu.name.contains(dto.getKeyword()).or(menu.engName.contains(dto.getKeyword())) : null;
    }

    public BooleanExpression inCategory (SearchRequestDto dto) {
        return !dto.getCategoryIds().isEmpty() ? menu.category.id.in(dto.getCategoryIds()) : null;
    }
}