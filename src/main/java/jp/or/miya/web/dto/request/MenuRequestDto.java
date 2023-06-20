package jp.or.miya.web.dto.request;

import jp.or.miya.domain.menu.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class MenuRequestDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Save {
        private String part;
        private String category;
        private String name;
        private String engName;
        private String temp;
        private String sizes;
        private LocalDateTime saleStartDt;
        private LocalDateTime saleEndDt;
        private Long price;
        private Integer season;
        private Integer pick;
        private String expl;

        @Builder
        public Save (String part, String category, String name, String engName, String temp, String sizes, LocalDateTime saleStartDt, LocalDateTime saleEndDt, Long price, Integer season, Integer pick, String expl) {
            this.part = part;
            this.category = category;
            this.name = name;
            this.engName = engName;
            this.temp = temp;
            this.sizes = sizes;
            this.saleStartDt = saleStartDt;
            this.saleEndDt = saleEndDt;
            this.price = price;
            this.season = season;
            this.pick = pick;
            this.expl = expl;
        }

        public Menu toEntity() {
            return Menu.builder()
                    .part(part)
                    .category(category)
                    .name(name)
                    .engName(engName)
                    .temp(temp)
                    .sizes(sizes)
                    .saleStartDt(saleStartDt)
                    .saleEndDt(saleEndDt)
                    .price(price)
                    .season(season)
                    .pick(pick)
                    .expl(expl)
                    .build();
        }
    }
}
