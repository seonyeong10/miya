package jp.or.miya.web.dto.response.menu;

import jp.or.miya.domain.item.Menu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MenuListResponseDto {
    private Long id;
    private CategoryResponseDto category;
    private String name;
    private String engName;
    private String temp;
    private String sizes;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int price;
    private String season;
    private String pick;
    private Long fileId = 0L;

    public MenuListResponseDto (Menu entity) {
        this.id = entity.getId();
        this.category = CategoryResponseDto.builder()
                .id(entity.getCategory().getId())
                .name(entity.getCategory().getName())
                .build();
        this.name = entity.getName();
        this.engName = entity.getEngName();
        this.temp = entity.getTemp();
        this.sizes = entity.getSizes();
        this.startDate = entity.getPeriod().getStartDate();
        this.endDate = entity.getPeriod().getEndDate();
        this.price = entity.getPrice();
        this.season = entity.getSeason();
        this.pick = entity.getPick();
        if(!entity.getAttachFiles().isEmpty()) {
            this.fileId = List.copyOf(entity.getAttachFiles()).get(0).getId(); // 수정 불가
        }
    }
}
