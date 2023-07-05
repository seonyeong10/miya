package jp.or.miya.web.dto.response.menu;

import jp.or.miya.domain.base.Category;
import jp.or.miya.domain.menu.Menu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class MenuListResponseDto {
    private Long id;
    private String part;
    private CategoryResponseDto category;
    private String name;
    private String engName;
    private String temp;
    private String sizes;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;
    private Integer season;
    private Integer pick;
    private Long fileId = 0L;

    public MenuListResponseDto (Menu entity) {
        this.id = entity.getId();
        this.part = entity.getPart();
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
