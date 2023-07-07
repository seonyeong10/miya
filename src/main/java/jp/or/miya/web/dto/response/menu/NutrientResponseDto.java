package jp.or.miya.web.dto.response.menu;

import jp.or.miya.domain.item.Nutrient;
import lombok.Getter;

@Getter
public class NutrientResponseDto {
    private Long calorie;
    private Integer carbohydrate;
    private Integer sugar;
    private Integer protein;
    private Integer fat;
    private Integer saturFat;
    private Integer transFat;
    private Integer cholesterol;
    private Integer caffeine;
    private Integer sodium;

    public NutrientResponseDto (Nutrient entity) {
        this.calorie = entity.getCalorie();
        this.carbohydrate = entity.getCarbohydrate();
        this.sugar = entity.getSugar();
        this.protein = entity.getProtein();
        this.fat = entity.getFat();
        this.saturFat = entity.getSaturFat();
        this.transFat = entity.getTransFat();
        this.cholesterol = entity.getCholesterol();
        this.caffeine = entity.getCaffeine();
        this.sodium = entity.getSodium();
    }
}
