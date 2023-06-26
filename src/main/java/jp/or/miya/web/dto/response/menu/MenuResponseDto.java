package jp.or.miya.web.dto.response.menu;

import jp.or.miya.domain.menu.Menu;
import jp.or.miya.web.dto.response.file.FileResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class MenuResponseDto {
    private Long id;
    private String part;
    private String category;
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
    private Set<FileResponseDto> files;
    private NutrientResponseDto nutrient;

    public MenuResponseDto (Menu entity) {
        System.out.println(entity.getNutrient());

        this.id = entity.getId();
        this.part = entity.getPart();
        this.category = entity.getCategory();
        this.name = entity.getName();
        this.engName = entity.getEngName();
        this.temp = entity.getTemp();
        this.sizes = entity.getSizes();
        this.saleStartDt = entity.getSaleStartDt();
        this.saleEndDt = entity.getSaleEndDt();
        this.price = entity.getPrice();
        this.season = entity.getSeason();
        this.pick = entity.getPick();
        this.expl = entity.getExpl();
        this.files = entity.getAttachFiles().stream().sorted()
                .map(FileResponseDto::new)
                .collect(Collectors.toSet());
        this.nutrient = new NutrientResponseDto(entity.getNutrient());
    }
}
