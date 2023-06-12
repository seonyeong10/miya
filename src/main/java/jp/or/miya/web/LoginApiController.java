package jp.or.miya.web;

import jp.or.miya.domain.config.jwt.JwtToken;
import jp.or.miya.service.login.LoginService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/api/test")
    public JwtToken test(
            @RequestBody Map<String, String> loginForm
    ) {
        JwtToken token = service.login(loginForm.get("id"), loginForm.get("pw"));
        return token;
    }

    @PostMapping("/api/adm/login")
    public JwtToken admLogin(
            @RequestBody Map<String, String> loginForm
    ) {
        JwtToken token = service.login(loginForm.get("id"), loginForm.get("pw"));
        return token;
    }
}
