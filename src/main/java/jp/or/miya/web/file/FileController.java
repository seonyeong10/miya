package jp.or.miya.web.file;

import jp.or.miya.service.file.FileService;
import jp.or.miya.web.dto.request.AttachFileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /** 첨부파일 저장  */
    @PostMapping("/api/files/{parentId}")
    public ResponseEntity<?> upload (
            @PathVariable("parentId") Long parentId,
            @RequestParam(value = "dir") String dir,
            @RequestPart(value = "file") List<MultipartFile> files
    ) {
        AttachFileRequestDto.Upload upload = AttachFileRequestDto.Upload.builder()
                .dir(dir)
                .parentId(parentId)
                .build();
        return fileService.save(upload, files);
    }

    /** 이미지 path 조회 */
    @GetMapping("/api/image/{fileId}")
    public ResponseEntity<Resource> viewImage (
            @PathVariable(value = "fileId") Long fileId
    ) {
        return fileService.viewImage(fileId);
    };

    /** 첨부파일 삭제 */
    @DeleteMapping("/api/files/{parentId}")
    public Long delete (
            @PathVariable(value = "parentId") Long parentId,
            @RequestParam(value = "remove") ArrayList<Long> remove
    ) {
        return fileService.delete(parentId, remove);
    }
}
