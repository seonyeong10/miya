package jp.or.miya.web.dto.error;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Getter
@NoArgsConstructor
public class ErrorDetail {
    private String objectName;
    private String field;
    private String code;
    private String message;

    @Builder
    public ErrorDetail(FieldError fieldError, MessageSource messageSource, Locale locale) {
        this.objectName = fieldError.getObjectName();
        this.field = fieldError.getField();
        this.code = fieldError.getCode();
        /** 영문 메시지를 설정했을 때 요청받은 Locale 값으로 메시지 언어를 알맞게 응답할 수 있다. 이 과정이 없으면 앞서 하려던 메시지 값이 기본 값으로 나가게 된다. */
        this.message = messageSource.getMessage(fieldError, locale); // 국제화
    }
}
