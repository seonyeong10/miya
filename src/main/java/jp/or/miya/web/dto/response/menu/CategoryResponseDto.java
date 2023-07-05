package jp.or.miya.web.dto.response.menu;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryResponseDto {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "CategoryResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
