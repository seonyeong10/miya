package jp.or.miya.domain.menu;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import jp.or.miya.domain.Period;
import jp.or.miya.domain.base.Category;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@NoArgsConstructor
@Entity
public class Menu extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String part;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column
    private String name;
    @Column
    private String engName;
    @Column
    private String temp;
    @Column
    private String sizes;
    @Embedded
    private Period period;
    @Column
    private Long price;
    @Column
    private Integer season;
    @Column
    private Integer pick;
    @Lob
    private String expl;

    // 카테시안 곱으로 인한 중복 데이터 제거
    @OneToMany(mappedBy = "menu")
    private List<AttachFile> attachFiles = new ArrayList<>();
    // Menu 저장할 때 Nutrient 함께 저장
    // orphanRemoval = true 고아 객체 제거
    // cascade = CascadeType.ALL Menu 저장할 때 attachFile 함께 저장
    @OneToOne(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Nutrient nutrient = new Nutrient();

    @Builder
    public Menu (Long id, String part, Category category, String name, String engName, String temp, String sizes, LocalDateTime startDate, LocalDateTime endDate, Long price, Integer season, Integer pick, String expl, Nutrient nutrient) {
        this.id = id;
        this.part = part;
        this.category = category;
        this.name = name;
        this.engName = engName;
        this.temp = temp;
        this.sizes = sizes;
        this.period = Period.builder().startDate(startDate).endDate(endDate).build();
        this.price = price;
        this.season = season;
        this.pick = pick;
        this.expl = expl;
        this.nutrient = nutrient;
    }

    public void addNutrient (Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    public void addCategory (Category category) {
        this.category = category;
    }

    public void update (MenuUpdateRequestDto dto) {
        this.name = dto.getName();
        this.engName = dto.getEngName();
        this.temp = dto.getTemp();
        this.sizes = dto.getSizes();
        this.period = Period.builder().startDate(dto.getStartDate()).endDate(dto.getEndDate()).build();
        this.price = dto.getPrice();
        this.season = dto.getSeason();
        this.pick = dto.getPick();
        this.expl = dto.getExpl();
    }
}
