package jp.or.miya.domain.item;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import jp.or.miya.domain.Period;
import jp.or.miya.domain.base.Category;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private String engName;

    private int price;

    @Embedded
    private Period period;

    private String season;

    private String pick;

    @Lob
    private String etc;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachFile> attachFiles = new ArrayList<>();

    public Item(Category category, String name, String engName, int price, Period period, String season, String pick, String etc) {
        this.category = category;
        this.name = name;
        this.engName = engName;
        this.price = price;
        this.period = period;
        this.season = season;
        this.pick = pick;
        this.etc = etc;
    }

    public Period getPeriod () {
        return period == null ? Period.builder().build() : period;
    }

    /* 연관관계 메서드 */
    public void addCategory (Category category) {
        this.category = category;
    }

    /* 비지니스 메서드 */
    public void update (MenuUpdateRequestDto dto) {
        this.name = dto.getName();
        this.engName = dto.getEngName();
        this.period = Period.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        this.price = dto.getPrice();
        this.season = dto.getSeason();
        this.pick = dto.getPick();
        this.etc = dto.getEtc();
    }
}
