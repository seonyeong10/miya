package jp.or.miya.web.dto.request.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.Nutrient;
import jp.or.miya.web.dto.request.AttachFileRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MenuSaveRequestDto {
    private String part;
    private String category;
    private String name;
    private String engName;
    private String temp;
    private String sizes;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime saleStartDt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime saleEndDt;
    private Long price;
    private Integer season;
    private Integer pick;
    private String expl;
    private Long modEmp;

    private Long menuId;
    private Long calorie;
    private int carbohydrate;
    private int sugar;
    private int protein;
    private int fat;
    private int saturFat;
    private int transFat;
    private int cholesterol;
    private int caffeine;
    private int sodium;

    // 첨부파일
    private String dir;
    private Set<AttachFile> attachFiles = new HashSet<>();

    @Builder
    public MenuSaveRequestDto (
            String part, String category, String name, String engName, String temp, String sizes, LocalDateTime saleStartDt, LocalDateTime saleEndDt, Long price, Integer season, Integer pick, String expl, Long modEmp,
            Long calorie, int carbohydrate, int sugar, int protein, int fat, int saturFat, int transFat, int cholesterol, int caffeine, int sodium,
            String dir
    ) {
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
        this.dir = dir;
    }

    public Menu toEntity() {
        Nutrient nutrient = Nutrient.builder()
                .menuId(menuId)
                .calorie(calorie)
                .carbohydrate(carbohydrate)
                .sugar(sugar)
                .protein(protein)
                .fat(fat)
                .saturFat(saturFat)
                .transFat(transFat)
                .cholesterol(cholesterol)
                .caffeine(caffeine)
                .sodium(sodium)
                .build();

//        Set<AttachFile> attachFileSet = attachFiles.stream().map(AttachFile::new).collect(Collectors.toSet());

        return Menu.builder()
                .part(part)
                .category(category)
                .name(name)
                .engName(engName)
                .temp(temp)
                .sizes(sizes)
                .saleStartDt(saleStartDt)
                .saleEndDt(saleEndDt)
                .price(price)
                .season(season)
                .pick(pick)
                .expl(expl)
                .modEmp(modEmp)
                .nutrient(nutrient)
                .attachFiles(attachFiles)
                .build();
    }
}
