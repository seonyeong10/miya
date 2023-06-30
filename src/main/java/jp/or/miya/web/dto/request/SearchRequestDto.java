package jp.or.miya.web.dto.request;

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
    private List<String> category = new ArrayList<>();
    private String keyword;

    @Builder
    public SearchRequestDto (int page, List<String> category, String keyword) {
        this.page = page;
        this.category = category;
        this.keyword = keyword;
    }
}
