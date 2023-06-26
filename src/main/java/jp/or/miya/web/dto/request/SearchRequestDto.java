package jp.or.miya.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequestDto {
    private int page;

    @Builder
    public SearchRequestDto (int page) {
        this.page = page;
    }
}
