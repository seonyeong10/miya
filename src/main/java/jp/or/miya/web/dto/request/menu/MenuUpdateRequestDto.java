package jp.or.miya.web.dto.request.menu;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jp.or.miya.web.dto.request.AttachFileRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuUpdateRequestDto {
    private String name;
    private String engName;
    private String temp;
    private String sizes;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;
    private Long price;
    private Integer season;
    private Integer pick;
    private String expl;
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
    private String dir; // 첨부파일 경로
    private List<AttachFileRequestDto.Save> attachFiles = new ArrayList<>(); // 등록할 첨부파일
    List<Long> remove = new ArrayList<>(); // 삭제할 첨부파일

    @Builder
    public MenuUpdateRequestDto (
            String name, String engName, String temp, String sizes, LocalDateTime startDate, LocalDateTime endDate, Long price, Integer season, Integer pick, String expl,
            Long calorie, int carbohydrate, int sugar, int protein, int fat, int saturFat, int transFat, int cholesterol, int caffeine, int sodium,
            String dir, List<Long> remove
    ) {
        this.name = name;
        this.engName = engName;
        this.temp = temp;
        this.sizes = sizes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.season = season;
        this.pick = pick;
        this.expl = expl;
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
        this.remove = remove;
    }

}
