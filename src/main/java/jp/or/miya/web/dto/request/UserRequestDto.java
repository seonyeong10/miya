package jp.or.miya.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserRequestDto {

    @Getter
    @Setter
    public static class LoginAdm {
        @NotEmpty(message = "사번은 필수 입력값입니다.")
        private String empNo;
        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        private String pw;
        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(empNo, pw);
        }
    }

    @Getter
    @Setter
    public static class Reissue {
        @NotEmpty(message = "accessToken 을 입력해주세요.")
        private String accessToken;
        @NotEmpty(message = "refreshToken 을 입력해주세요.")
        private String refreshToken;
    }
}
