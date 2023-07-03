package jp.or.miya.service.staff;

import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.staff.Staff;
import jp.or.miya.domain.staff.StaffRepository;
import jp.or.miya.domain.staff.Work;
import jp.or.miya.lib.FileUtils;
import jp.or.miya.web.dto.request.staff.StaffSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaffService {
    private final String BASE_DIR = "D:/03. Project/07. Miya/uploads";
    private final StaffRepository repository;
    private final FileUtils fileUtils;
    public ResponseEntity<?> saveWithFile (StaffSaveRequestDto requestDto, List<MultipartFile> files) {
        // 직원번호 생성
        String empNo = getNewEmpNo(repository.findLatestId());
        System.out.println(empNo);

        if(empNo == null) {
            return new ResponseEntity<>("직원번호 생성 실패", HttpStatus.BAD_REQUEST);
        }

        // 첨부파일 저장
        Set<AttachFile> attachFiles = new HashSet<>();
        try {
            fileUtils.saveFiles(attachFiles, files, "/staff", null);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
        }

        // 데이터 저장
        requestDto.setEmpNo(empNo);
        Staff staff = repository.save(requestDto.toEntity(attachFiles));
        // staff_id 업데이트
        Set<AttachFile> savedFile = staff.getAttachFiles();
        savedFile.stream().forEach(file -> file.setStaff(staff));

        return ResponseEntity.ok(staff.getEmpNo());
    }

    public String getNewEmpNo (String latestEmpNo) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        int seq = 1;

        if(today.equals(latestEmpNo.substring(0,6))) {
            seq = Integer.parseInt(latestEmpNo.substring(6)) + 1;
        }

        return today + String.format("%03d", seq);
    }
}
