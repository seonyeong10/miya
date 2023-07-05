package jp.or.miya.web.dto.request;

import jp.or.miya.domain.base.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequestDto {
    private int page;
    private Long parentCategoryId;
    private List<Long> categoryIds = new ArrayList<>();
    private String keyword;

    @Builder
    public SearchRequestDto (int page, Long parentCategoryId, List<Long> categoryIds, String keyword) {
        this.page = page;
        this.parentCategoryId = parentCategoryId;
        this.categoryIds = categoryIds;
        this.keyword = keyword;
    }
}
