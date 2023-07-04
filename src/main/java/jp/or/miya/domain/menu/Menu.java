package jp.or.miya.domain.menu;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
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
    @Column
    private String category;
    @Column
    private String name;
    @Column
    private String engName;
    @Column
    private String temp;
    @Column
    private String sizes;
    @Column
    private LocalDateTime saleStartDt;
    @Column
    private LocalDateTime saleEndDt;
    @Column
    private Long price;
    @Column
    private Integer season;
    @Column
    private Integer pick;
    @Lob
    private String expl;
    private Long modEmp;

    // 카테시안 곱으로 인한 중복 데이터 제거
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AttachFile> attachFiles = new LinkedHashSet<>();
    @OneToOne(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Nutrient nutrient = new Nutrient();

    @Builder
    public Menu (Long id, String part, String category, String name, String engName, String temp, String sizes, LocalDateTime saleStartDt, LocalDateTime saleEndDt, Long price, Integer season, Integer pick, String expl, Long modEmp, Nutrient nutrient) {
        this.id = id;
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
        this.modEmp = modEmp;
        this.nutrient = nutrient;
    }

    public void addNutrient (Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    public void update (MenuUpdateRequestDto dto) {
        this.name = dto.getName();
        this.engName = dto.getEngName();
        this.temp = dto.getTemp();
        this.sizes = dto.getSizes();
        this.saleStartDt = dto.getSaleStartDt();
        this.saleEndDt = dto.getSaleEndDt();
        this.price = dto.getPrice();
        this.season = dto.getSeason();
        this.pick = dto.getPick();
        this.expl = dto.getExpl();
        this.modEmp = dto.getModEmp();
    }
}
