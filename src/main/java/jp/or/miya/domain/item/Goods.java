package jp.or.miya.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jp.or.miya.domain.Period;
import jp.or.miya.domain.base.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("G")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods extends Item {
    private int quantity;

    public Goods(int quantity) {
        this.quantity = quantity;
    }

    @Builder
    public Goods(Category category, String name, String engName, int price, Period period, String season, String pick, String etc, int quantity) {
        super(category, name, engName, price, period, season, pick, etc);
        this.quantity = quantity;
    }
}
