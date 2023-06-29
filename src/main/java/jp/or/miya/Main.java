package jp.or.miya;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
//        System.out.println(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli());
//        Path dir = Paths.get("D:/03. Project/07. Miya/uploads/menus/foods");
//        Path file = Paths.get("D:/03. Project/07. Miya/uploads/menus/foods/hello.txt");
//        try {
//            fileUploadTest(dir, file);
//        } catch (IOException e) {
//            System.out.println(e);
//            log.info(e.getMessage());
//        }
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