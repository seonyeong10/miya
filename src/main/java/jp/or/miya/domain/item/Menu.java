package jp.or.miya.domain.item;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import jp.or.miya.domain.Period;
import jp.or.miya.domain.base.Category;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("M")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends Item {
    private String temp;
    private String sizes;

    // Menu 저장할 때 Nutrient 함께 저장
    // orphanRemoval = true 고아 객체 제거
    // cascade = CascadeType.ALL Menu 저장할 때 attachFile 함께 저장
    @OneToOne(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Nutrient nutrient = new Nutrient();

    @Builder
    public Menu(Category category, String name, String engName, int price, Period period, String season, String pick, String etc, String temp, String sizes, Nutrient nutrient) {
        super(category, name, engName, price, period, season, pick, etc);
        this.temp = temp;
        this.sizes = sizes;
        this.nutrient = nutrient;
    }

    public Nutrient getNutrient () {
        return nutrient == null ? Nutrient.builder().build() : nutrient;
    }

    /* 연관관계 메서드 */
    public void addNutrient (Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    /* 비지니스 메서드 */
    public void update (MenuUpdateRequestDto dto) {
        super.update(dto);
        this.temp = dto.getTemp();
        this.sizes = dto.getSizes();
    }

}
