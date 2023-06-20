package jp.or.miya;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Slf4j
public class Main {
    public static void main(String[] args) {
        System.out.println(getLocalDateTime("2023-07-01T23:59:59.999"));
    }

    public static LocalDateTime getLocalDateTime (String date) {
        return LocalDateTime.parse(date);
    }

    private String BCryptEncode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return (encoder.encode("miya"));
    }
}