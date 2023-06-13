package jp.or.miya.web;

import jp.or.miya.config.jwt.JwtToken;
import jp.or.miya.service.login.LoginService;
import jp.or.miya.web.dto.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LoginApiController {

    private final LoginService service;

    @PostMapping("/api/adm/login")
    public ResponseEntity<JwtToken> admLogin(
            @RequestBody UserRequestDto.LoginAdm login
    ) {
        JwtToken token = service.login(login.getEmpNo(), login.getPw());
        return ResponseEntity.ok(token);
    }

    /**
     * @param reissue : 토큰 갱신을 위해서는 accessToken, refreshToken 모두 필요
     * @return
     */
    @PostMapping("/api/reissue")
    public ResponseEntity<?> reissue(
            @RequestBody UserRequestDto.Reissue reissue
    ) {
        return service.reissue(reissue);
    }
}
