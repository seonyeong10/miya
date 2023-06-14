package jp.or.miya;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("-----------log 테스트");
    }

    private String BCryptEncode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return (encoder.encode("miya"));
    }
}