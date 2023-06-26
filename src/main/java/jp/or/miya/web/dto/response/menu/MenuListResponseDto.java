package jp.or.miya.web.dto.response.menu;

import jp.or.miya.domain.menu.Menu;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class MenuListResponseDto {
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
    private Long fileId = 0L;

    public MenuListResponseDto (Menu entity) {
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
        if(!entity.getAttachFiles().isEmpty()) {
            this.fileId = entity.getAttachFiles().stream().sorted().collect(Collectors.toList()).get(0).getId();
        }
    }
}
