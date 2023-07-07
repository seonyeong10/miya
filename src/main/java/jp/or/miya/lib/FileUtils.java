package jp.or.miya.lib;

import jp.or.miya.domain.file.AttachFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
import java.util.NoSuchElementException;

@Component
public class FileUtils {
    @Value("${file.upload.dir}")
    private static final String BASE_DIR = "D:/03. Project/07. Miya/uploads";

    /**
     * 첨부파일을 저장한다.
     * @param multipartFiles : 사용자가 등록한 첨부파일
     * @param dir : 업로드 경로
     * @throws IOException
     */
    public static List<AttachFile> saveFiles (List<MultipartFile> multipartFiles, String dir) throws IOException {
        List<AttachFile> savedFiles = new ArrayList<>(); // return value
        Path path = Paths.get(BASE_DIR + dir); // 저장 경로

        for(int i=0 ; i < multipartFiles.size() ; i++) {
            MultipartFile f = multipartFiles.get(i);
            String originalName = f.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf("."));

            AttachFile saveFile = AttachFile.builder()
                    .name(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() + extension)
                    .orgName(originalName)
                    .dir(dir)
                    .seq(i+1)
                    .build();

            Path filePath = Paths.get(BASE_DIR + dir + File.separator + saveFile.getName());

            // 파일 저장
            if(!Files.isDirectory(path))
                Files.createDirectories(path);

            f.transferTo(new File(filePath.toString())); // 실제 파일 저장
            savedFiles.add(saveFile);
        } // for

        return savedFiles;
    }

    /**
     * 첨부파일을 삭제한다.
     * @param originalFiles : 기존 등록된 파일
     * @param remove : 사용자가 삭제 요청한 파일
     * @throws IOException
     */
    public static List<AttachFile> deleteFile (List<AttachFile> originalFiles, List<Long> remove) throws  IOException {
        List<AttachFile> removedFiles = new ArrayList<>(); // return value

        if(remove.isEmpty() || remove == null) {
            return new ArrayList<>();
        }

        for(Long i : remove) {
            AttachFile file = originalFiles.stream().filter(f -> f.getId() == i).findAny().orElseThrow(() -> new NoSuchElementException("첨부파일이 없습니다. file_id = " + i));
            Path path = Paths.get(BASE_DIR + file.getDir() + File.separator + file.getName());

            Files.deleteIfExists(path); // 파일 삭제
            removedFiles.add(file);
        } // for

        return removedFiles;
    }

    public static String getBaseDir () {
        return BASE_DIR;
    }
}