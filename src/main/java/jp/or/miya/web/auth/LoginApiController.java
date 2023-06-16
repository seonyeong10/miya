package jp.or.miya.web.auth;

import jakarta.validation.Valid;
import jp.or.miya.service.auth.LoginService;
import jp.or.miya.web.dto.error.ErrorResult;
import jp.or.miya.web.dto.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;


@RequiredArgsConstructor
@RestController
public class LoginApiController {

    private final LoginService service;
    private final MessageSource messageSource;

    @PostMapping("/api/adm/login")
    public ResponseEntity<?> admLogin(
            @RequestBody @Valid UserRequestDto.LoginAdm login,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }
        return service.login(login.getEmpNo(), login.getPw());
    }

    /**
     * @param reissue : 토큰 갱신을 위해서는 accessToken, refreshToken 모두 필요
     * @return
     */
    @PostMapping("/api/reissue")
    public ResponseEntity<?> reissue(
            @RequestBody @Valid UserRequestDto.Reissue reissue,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }
        return service.reissue(reissue);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(
            @RequestBody @Valid UserRequestDto.Logout logout,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }
        return service.logout(logout);
    }
}
