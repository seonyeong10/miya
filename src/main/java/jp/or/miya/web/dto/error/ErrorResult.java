package jp.or.miya.web.dto.error;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;

@Getter
@NoArgsConstructor
public class ErrorResult {
    private List<ErrorDetail> errorDetails;

    @Builder
    public ErrorResult(Errors errors, MessageSource messageSource, Locale locale) {
        this.errorDetails = errors.getFieldErrors()
                .stream()
                .map(error -> ErrorDetail.builder().fieldError(error).messageSource(messageSource).locale(locale).build())
                .toList();
    }
}
