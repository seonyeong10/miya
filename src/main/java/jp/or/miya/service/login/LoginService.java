package jp.or.miya.service.login;

import jp.or.miya.domain.config.jwt.JwtToken;
import jp.or.miya.domain.config.jwt.JwtTokenProvider;
import jp.or.miya.domain.user.StaffLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final BCryptPasswordEncoder encoder;
    private final StaffLoginRepository staffLoginRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtToken login(String id, String pw) {
        // Authenication 객체 생성
        // id, pw를 기반으로 Authentication 객체 생성
        // authenticated : 인증여부 확인, default = false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, pw);
        // 사용자 비밀번호 검증
        // authenticate 메서드가 실행될 때 CustomUserDetail 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 검증된 인증 정보로 JWT 토큰 생성
        JwtToken token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
