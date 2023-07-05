package jp.or.miya;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Main {
    public static void main(String[] args) {
       String test = "230703001";
       String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
       int num = 1;

       if(today.equals(test.substring(0,6))) {
           num = Integer.parseInt(test.substring(6)) + 1;
       }
       String code = today + String.format("%03d", num);
       System.out.println(code);
    }

    public static void fileUploadTest (Path dir, Path file) throws IOException {
        // 디렉토리 생성
        if(!Files.isDirectory(dir)) {
            Files.createDirectories(dir);
        }

        // 파일 저장
        if(!Files.exists(file)) {
            Files.createFile(file);
        }
    }

    public static LocalDateTime getLocalDateTime (String date) {
        return LocalDateTime.parse(date);
    }

    private String BCryptEncode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return (encoder.encode("miya"));
    }
}