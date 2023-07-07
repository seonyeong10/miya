package jp.or.miya.web.dto.response.menu;

import jp.or.miya.domain.base.Category;
import jp.or.miya.domain.item.Menu;
import jp.or.miya.web.dto.response.file.FileResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MenuResponseDto {
    private Long id;
    private Category category;
    private String name;
    private String engName;
    private String temp;
    private String sizes;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int price;
    private String season;
    private String pick;
    private String etc;
    private List<FileResponseDto> files = new ArrayList<>();
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
        this.id = entity.getId();
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
        this.etc = entity.getEtc();
        this.files = entity.getAttachFiles().stream()
                .map(FileResponseDto::new)
                .collect(Collectors.toList());
        this.calorie = entity.getNutrient().getCalorie();
        this.carbohydrate = entity.getNutrient().getCarbohydrate();
        this.sugar = entity.getNutrient().getSugar();
        this.protein = entity.getNutrient().getProtein();
        this.fat = entity.getNutrient().getFat();
        this.saturFat = entity.getNutrient().getSaturFat();
        this.transFat = entity.getNutrient().getTransFat();
        this.cholesterol = entity.getNutrient().getCholesterol();
        this.caffeine = entity.getNutrient().getCaffeine();
        this.sodium = entity.getNutrient().getSodium();
    }
}
