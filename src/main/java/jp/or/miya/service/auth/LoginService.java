package jp.or.miya.service.auth;

import jp.or.miya.config.jwt.JwtToken;
import jp.or.miya.config.jwt.JwtTokenProvider;
import jp.or.miya.domain.user.StaffLoginRepository;
import jp.or.miya.web.dto.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final BCryptPasswordEncoder encoder;
    private final StaffLoginRepository staffLoginRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    public ResponseEntity<?> login(String id, String pw) {
        StringBuilder errorMessage = new StringBuilder();
        // Authenication 객체 생성
        // 1.id, pw를 기반으로 Authentication 객체 생성
        // authenticated : 인증여부 확인, default = false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, pw);

        try {
            // 2.사용자 비밀번호 검증
            // authenticate 메서드가 실행될 때 CustomUserDetail 에서 만든 loadUserByUsername 메서드 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3.검증된 인증 정보로 JWT 토큰 생성
            JwtToken token = jwtTokenProvider.generateToken(authentication);

            // 4.RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제)
            redisTemplate.opsForValue()
                    .set("RT:" + authentication.getName(), token.getRefreshToken(), token.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            // ID, PW 불일치
            errorMessage.append(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage.toString());
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }
    }

    public ResponseEntity<?> reissue(UserRequestDto.Reissue reissue) {
        // 1.Refresh token 검증
        if(!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            return new ResponseEntity<>("유효하지 않은 Token 입니다.", HttpStatus.BAD_REQUEST);
        }

        // 2.Access token 에서 user id 가져오기
        Authentication authentication = jwtTokenProvider.getAuthenication(reissue.getAccessToken());

        // 3.Redis 에서 id 를 기반으로 저장된 Refresh token 값 가져오기
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + authentication.getName());
        // 로그아웃되어 Redis 에 refreshToken 이 존재하지 않는 경우 -- 추가해야 함
        if(!refreshToken.equals(reissue.getRefreshToken())) {
            return new ResponseEntity<>("refreshToken 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 4.새로운 토큰 생성
        JwtToken token = jwtTokenProvider.generateToken(authentication);

        // 5.Refresh token Redis Update
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), token.getRefreshToken(), token.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<?> logout(UserRequestDto.Logout logout) {
        // 1.AccessToken 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        // 2.AccessToken 으로부터 id 가져오기
        Authentication authentication = jwtTokenProvider.getAuthenication(logout.getAccessToken());

        // 3.Redis 에서 해당 id로 저장된 refreshToken 이 있는지 확인하고 삭제
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // refreshToken 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4.해당 AccessToken 유효시간 가지고 와서 BlakList 로 저장
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
