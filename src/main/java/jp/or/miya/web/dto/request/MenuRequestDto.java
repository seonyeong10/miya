package jp.or.miya.web.dto.request;

import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.Nutrient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class MenuRequestDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Find {
        private int page;

        @Builder
        public Find (int page) {
            this.page = page;
        }
    }
}
