package jp.or.miya.web.file;

import jp.or.miya.service.file.FileService;
import jp.or.miya.web.dto.request.AttachFileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/api/files/{boardId}")
    public ResponseEntity<?> upload (
            @PathVariable("boardId") Long boardId,
            @RequestParam(value = "modEmp") Long modEmp,
            @RequestParam(value = "dir") String dir,
            @RequestPart(value = "file") List<MultipartFile> files
    ) {
        AttachFileRequestDto.Upload upload = AttachFileRequestDto.Upload.builder()
                .dir(dir)
                .boardId(boardId)
                .modEmp(modEmp)
                .build();
//        return ResponseEntity.ok("");
        return fileService.upload(upload, files);
    }
}
