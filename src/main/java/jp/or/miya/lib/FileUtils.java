package jp.or.miya.lib;

import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.menu.Menu;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

public class FileUtils {
    private final String BASE_DIR = "D:/03. Project/07. Miya/uploads";

    /**
     * 첨부파일을 저장한다.
     * @param attachFiles : DB에 저장될 객체
     * @param multipartFiles : request로 전달받은 객체
     * @param dir : 업로드 경로
     * @param menu : 메뉴 엔티티
     * @throws IOException
     */
    public void saveFiles (Set<AttachFile> attachFiles, List<MultipartFile> multipartFiles, String dir, Menu menu) throws IOException {
        Path path = Paths.get(BASE_DIR + dir);
        for(MultipartFile f : multipartFiles) {
            String originalName = f.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf("."));

            AttachFile saveFile = AttachFile.builder()
                    .name(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() + extension)
                    .orgName(originalName)
                    .dir(dir)
                    .menu(menu != null ? menu : null)
                    .build();

            Path filePath = Paths.get(BASE_DIR + dir + File.separator + saveFile.getName());

            // 파일 저장
            if(!Files.isDirectory(path))
                Files.createDirectories(path);

            f.transferTo(new File(filePath.toString()));
            attachFiles.add(saveFile);
        } // for
    }

    /**
     * 첨부파일을 삭제한다.
     * @param attachFiles : DB에 저장될 객체
     * @param remove : 삭제할 파일 Id List
     * @throws IOException
     */
    public void deleteFile (Set<AttachFile> attachFiles, List<Long> remove) throws  IOException {
        for(Long i : remove) {
            AttachFile file = attachFiles.stream().filter(f -> f.getId() == i).findAny().orElse(null);
            Path path = Paths.get(BASE_DIR + file.getDir() + File.separator + file.getName());

            // 파일 삭제
            Files.deleteIfExists(path);
            attachFiles.remove(file);
        } // for
    }
}