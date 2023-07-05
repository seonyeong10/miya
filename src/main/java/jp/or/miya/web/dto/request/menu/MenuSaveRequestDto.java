package jp.or.miya.web.dto.request.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jp.or.miya.domain.base.Category;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.Nutrient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MenuSaveRequestDto {
    private Long categoryId;
    private String name;
    private String engName;
    private String temp;
    private String sizes;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;
    private Long price;
    private Integer season;
    private Integer pick;
    private String expl;

    private Long menuId;
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

    // 첨부파일
    private String dir;

    @Builder
    public MenuSaveRequestDto (
            Long categoryId, String name, String engName, String temp, String sizes, LocalDateTime startDate, LocalDateTime endDate, Long price, Integer season, Integer pick, String expl,
            Long calorie, int carbohydrate, int sugar, int protein, int fat, int saturFat, int transFat, int cholesterol, int caffeine, int sodium,
            String dir
    ) {
        this.categoryId = categoryId;
        this.name = name;
        this.engName = engName;
        this.temp = temp;
        this.sizes = sizes;
        this.startDate = startDate;
        this.endDate = endDate;
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
        this.dir = dir;
    }

    public Menu toMenuEntity() {
        return Menu.builder()
                .name(name)
                .engName(engName)
                .temp(temp)
                .sizes(sizes)
                .startDate(startDate)
                .endDate(endDate)
                .price(price)
                .season(season)
                .pick(pick)
                .expl(expl)
                .build();
    }

    public Nutrient toNutrientEntity (Menu menu) {
        return Nutrient.builder()
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
                .menu(menu)
                .build();
    }
}
