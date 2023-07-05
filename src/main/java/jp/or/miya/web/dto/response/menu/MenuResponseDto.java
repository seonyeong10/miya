package jp.or.miya.web.dto.response.menu;

import jp.or.miya.domain.base.Category;
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
    private Category category;
    private String name;
    private String engName;
    private String temp;
    private String sizes;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;
    private Integer season;
    private Integer pick;
    private String expl;
    private Set<FileResponseDto> files;
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

    public MenuResponseDto (Menu entity) {
        NutrientResponseDto nutrient = new NutrientResponseDto(entity.getNutrient());
        this.id = entity.getId();
        this.part = entity.getPart();
        this.category = entity.getCategory();
        this.name = entity.getName();
        this.engName = entity.getEngName();
        this.temp = entity.getTemp();
        this.sizes = entity.getSizes();
        this.startDate = entity.getPeriod().getStartDate();
        this.endDate = entity.getPeriod().getEndDate();
        this.price = entity.getPrice();
        this.season = entity.getSeason();
        this.pick = entity.getPick();
        this.expl = entity.getExpl();
        this.files = entity.getAttachFiles().stream()
                .map(FileResponseDto::new)
                .collect(Collectors.toSet());
        this.calorie = nutrient.getCalorie();
        this.carbohydrate = nutrient.getCarbohydrate();
        this.sugar = nutrient.getSugar();
        this.protein = nutrient.getProtein();
        this.fat = nutrient.getFat();
        this.saturFat = nutrient.getSaturFat();
        this.transFat = nutrient.getTransFat();
        this.cholesterol = nutrient.getCholesterol();
        this.caffeine = nutrient.getCaffeine();
        this.sodium = nutrient.getSodium();
    }
}
