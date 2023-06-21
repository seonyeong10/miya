package jp.or.miya.service.file;

import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.file.AttachFileRepository;
import jp.or.miya.web.dto.request.AttachFileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor

public class FileService {

    private final String BASE_DIR = "D:/03. Project/07. Miya/uploads";
    private final AttachFileRepository attachFileRepository;

    public ResponseEntity<?> upload (AttachFileRequestDto.Upload upload, List<MultipartFile> files) {
        for(MultipartFile f : files) {
            String orgName = f.getOriginalFilename();
            String ext = orgName.substring(orgName.lastIndexOf("."));
            AttachFile attachFile = AttachFile.builder()
                    .boardId(upload.getBoardId())
                    .name(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() + ext)
                    .orgName(orgName)
                    .modEmp(upload.getModEmp())
                    .dir(upload.getDir())
                    .build();

            Path dir = Paths.get(BASE_DIR + attachFile.getDir());
            Path fPath = Paths.get(BASE_DIR + attachFile.getDir() + File.separator + attachFile.getName());

            try {
                if(!Files.isDirectory(dir)) {
                    Files.createDirectories(dir);
                }
                // 파일 저장
                f.transferTo(new File(fPath.toString()));
                // 데이터 저장
                attachFileRepository.save(attachFile);
            } catch (IOException e) {
                System.out.println(e);
                return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
            }
        }

        return ResponseEntity.ok("");
    }
}
