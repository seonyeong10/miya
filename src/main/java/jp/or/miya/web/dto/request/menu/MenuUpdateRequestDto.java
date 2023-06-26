package jp.or.miya.web.dto.request.menu;

import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.Nutrient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MenuUpdateRequestDto {
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

    private Long calorie;
    private int carbohydrate;
    private int sugar;
    private int protein;
    private int fat;
    private int saturFat;
    private int transFat;
    private int cholesterol;
    private int caffeine;
    private int sodium;

    @Builder
    public MenuUpdateRequestDto (
            String name, String engName, String temp, String sizes, LocalDateTime saleStartDt, LocalDateTime saleEndDt, Long price, Integer season, Integer pick, String expl,
            Long calorie, int carbohydrate, int sugar, int protein, int fat, int saturFat, int transFat, int cholesterol, int caffeine, int sodium
    ) {
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
        this.calorie = calorie;
        this.carbohydrate = carbohydrate;
        this.sugar = sugar;
        this.protein = protein;
        this.fat = fat;
        this.saturFat = saturFat;
        this.transFat = transFat;
        this.cholesterol = cholesterol;
        this.caffeine = caffeine;
        this.sodium = sodium;
    }

    public Menu toEntity (Long id) {
        Nutrient nutrient = Nutrient.builder()
                .menuId(id)
                .calorie(calorie)
                .carbohydrate(carbohydrate)
                .sugar(sugar)
                .protein(protein)
                .fat(fat)
                .saturFat(saturFat)
                .transFat(transFat)
                .cholesterol(cholesterol)
                .caffeine(caffeine)
                .sodium(sodium)
                .build();
        return Menu.builder()
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
                .nutrient(nutrient)
                .build();
    }
}
