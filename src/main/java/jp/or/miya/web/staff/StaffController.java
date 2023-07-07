package jp.or.miya.web.staff;

import jakarta.servlet.http.HttpServletRequest;
import jp.or.miya.service.staff.StaffService;
import jp.or.miya.web.dto.request.staff.StaffSaveRequestDto;
import jp.or.miya.web.dto.request.staff.StaffUpdateRequestDto;
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
            @RequestPart(value = "file", required = false) List<MultipartFile> files
    ) throws Exception {
        return service.saveWithFile(requestDto, files);
    }

    @PostMapping("/api/v1/staff/{staff_id}")
    public ResponseEntity<?> updateWithFile (
            @PathVariable(value = "staff_id") String staffId,
            @RequestPart(value = "content", required = false) StaffUpdateRequestDto requestDto,
            @RequestPart(value = "file", required = false) List<MultipartFile> files
    ) throws Exception {
        return service.updateWithFIle(staffId, requestDto, files);
    }
}
