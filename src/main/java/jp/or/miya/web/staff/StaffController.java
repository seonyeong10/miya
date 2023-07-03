package jp.or.miya.web.staff;

import jakarta.servlet.http.HttpServletRequest;
import jp.or.miya.service.staff.StaffService;
import jp.or.miya.web.dto.request.staff.StaffSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StaffController {
    private final StaffService service;

    @PostMapping("/api/v1/staff")
    public ResponseEntity<?> saveWithFile (
            @RequestPart(value = "content", required = false) StaffSaveRequestDto requestDto,
            @RequestPart(value = "file", required = false) List<MultipartFile> files,
            HttpServletRequest request
    ) {
        System.out.println("save start ===== empNo : " + requestDto.getEmpNo());
        return service.saveWithFile(requestDto, files);
    }
}
