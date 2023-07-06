package jp.or.miya.service.file;

import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.file.repository.AttachFileRepository;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.repository.MenuRepository;
import jp.or.miya.web.dto.request.AttachFileRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final String BASE_DIR = "D:/03. Project/07. Miya/uploads";
    private final AttachFileRepository attachFileRepository;
    private final MenuRepository menuRepository;

    // 사용 안함
    public ResponseEntity<?> save (AttachFileRequestDto.Upload upload, List<MultipartFile> files) {
        // 부모테이블 정보 조회
        Menu menu = menuRepository.findById(upload.getParentId()).orElseThrow(IllegalAccessError::new);

        // 첨부파일 업로드
        for(MultipartFile f : files) {
            String orgName = f.getOriginalFilename();
            String ext = orgName.substring(orgName.lastIndexOf("."));
            AttachFile attachFile = AttachFile.builder()
//                    .boardId(upload.getBoardId())
                    .menu(menu)
                    .name(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() + ext)
                    .orgName(orgName)
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

    /** 이미지 path 조회 */
    public ResponseEntity<Resource> viewImage (Long fileId) {
        AttachFile attachFile = attachFileRepository.findById(fileId).orElse(null);
        if(attachFile == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        FileSystemResource resource = new FileSystemResource(BASE_DIR + attachFile.getDir() + File.separator + attachFile.getName());
        if(!resource.exists()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        Path path = Paths.get(BASE_DIR + attachFile.getDir() + File.separator + attachFile.getName());
        try {
            headers.add("ContentType", Files.probeContentType(path));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /** 파일 삭제 */
    public Long delete (Long parentId, ArrayList<Long> remove) {
        for(Long i : remove) {
            AttachFile attachFile = attachFileRepository.findById(i).orElse(null);

            if(attachFile == null) continue;

            // 파일 삭제
            Path path = Paths.get(BASE_DIR + attachFile.getDir() + File.separator + attachFile.getName());

            System.out.println(path);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
        // 저장 데이터 삭제
        attachFileRepository.deleteAllByIdInBatch(remove);

        return parentId;
    }
}
