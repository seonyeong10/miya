package jp.or.miya.domain.menu;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import jp.or.miya.domain.file.AttachFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column
    private String expl;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private List<AttachFile> attachFiles = new ArrayList<>();

    @Builder
    public Menu (Long id, String part, String category, String name, String engName, String temp, String sizes, LocalDateTime saleStartDt, LocalDateTime saleEndDt, Long price, Integer season, Integer pick, String expl) {
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
    }
}
