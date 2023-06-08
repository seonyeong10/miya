package jp.or.miya.web.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponsceDtoTest {
    @Test
    public void lombok_tech_test() {
        //given
        String name = "test";
        int amount = 1000;
        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);
        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
