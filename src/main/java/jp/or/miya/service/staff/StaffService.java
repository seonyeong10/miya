package jp.or.miya.service.staff;

import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.file.repository.AttachFileRepository;
import jp.or.miya.domain.staff.Staff;
import jp.or.miya.domain.staff.repository.StaffRepository;
import jp.or.miya.lib.FileUtils;
import jp.or.miya.web.dto.request.staff.StaffSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final StaffRepository staffRepository;
    private final AttachFileRepository fileRepository;
    private final FileUtils fileUtils;
    @Transactional
    public ResponseEntity<?> saveWithFile (StaffSaveRequestDto requestDto, List<MultipartFile> files) {
        // 직원번호 생성
        String id = createId(staffRepository.findLatestId());

        if(id == null) {
            return new ResponseEntity<>("직원번호 생성 실패", HttpStatus.BAD_REQUEST);
        }
        requestDto.setId(id);

        // 첨부파일 저장
        Set<AttachFile> attachFiles = new HashSet<>();
        try {
            fileUtils.saveFiles(attachFiles, files, "/staff");
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
        }

        // 직원 저장
        /**
         * save 기본 동작은
         * 식별자(@ID)가 null 이면 persist()
         * 식별자가 null 이 아니면 merge
         * 식별자를 직접 할당하게되면 엔티티 save 시 무조건 식별자 값이 존재하므로 merge 수행 (insert -> update)
         */
        Staff staff = staffRepository.save(requestDto.toEntity()); // insert
        // 첨부파일(사진) 저장
        attachFiles.stream().forEach(file -> file.addStaff(staff)); // staff_id 저장
        fileRepository.saveAll(attachFiles); // insert

        return ResponseEntity.ok(staff.getId());
    }

    public String createId (String latestEmpNo) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        int seq = 1;

        if(latestEmpNo != null && today.equals(latestEmpNo.substring(0,6))) {
            seq = Integer.parseInt(latestEmpNo.substring(6)) + 1;
        }

        return today + String.format("%03d", seq);
    }
}
