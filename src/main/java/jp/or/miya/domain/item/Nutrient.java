package jp.or.miya.domain.item;

import jakarta.persistence.*;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Nutrient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nutrient_id")
    private Long id;
    private Long calorie = 0L; // NPE
    private int carbohydrate;
    private int sugar;
    private int protein;
    private int fat;
    private int saturFat;
    private int transFat;
    private int cholesterol;
    private int caffeine;
    private int sodium;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Menu menu;

    @Builder
    public Nutrient (Long id, Long calorie, int carbohydrate, int sugar, int protein, int fat, int saturFat, int transFat, int cholesterol, int caffeine, int sodium, Menu menu) {
        this.id = id;
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
        this.menu = menu;
    }

    public void addMenu (Menu menu) {
        this.menu = menu;
        menu.addNutrient(this);
    }

    public void update (MenuUpdateRequestDto dto) {
        this.calorie = dto.getCalorie();
        this.carbohydrate = dto.getCarbohydrate();
        this.sugar = dto.getSugar();
        this.protein = dto.getProtein();
        this.fat = dto.getFat();
        this.saturFat = dto.getSaturFat();
        this.transFat = dto.getTransFat();
        this.cholesterol = dto.getCholesterol();
        this.caffeine = dto.getCaffeine();
        this.sodium = dto.getSodium();
    }
}
