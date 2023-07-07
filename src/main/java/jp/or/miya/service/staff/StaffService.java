package jp.or.miya.service.staff;

import jp.or.miya.config.SHA256;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.file.repository.AttachFileRepository;
import jp.or.miya.domain.staff.Staff;
import jp.or.miya.domain.staff.repository.StaffRepository;
import jp.or.miya.lib.FileUtils;
import jp.or.miya.web.dto.request.staff.StaffSaveRequestDto;
import jp.or.miya.web.dto.request.staff.StaffUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static jp.or.miya.lib.FileUtils.deleteFile;
import static jp.or.miya.lib.FileUtils.saveFiles;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaffService {
    private final String BASE_DIR = "D:/03. Project/07. Miya/uploads";
    private final StaffRepository staffRepository;
    private final AttachFileRepository fileRepository;
    @Transactional
    public ResponseEntity<?> saveWithFile (StaffSaveRequestDto requestDto, List<MultipartFile> files) throws NoSuchAlgorithmException {
        // 직원번호 생성
        String id = createId(staffRepository.findLatestId());
        List<AttachFile> attachFiles = new ArrayList<>(); // 첨부파일

        if(id == null) {
            return new ResponseEntity<>("직원번호 생성 실패", HttpStatus.BAD_REQUEST);
        }
        String pw = SHA256.encrypt(id); // 최초 비밀번호는 사번
        requestDto.setId(id);
        requestDto.setPw(pw);

        /**
         * save 기본 동작은
         * 식별자(@ID)가 null 이면 persist()
         * 식별자가 null 이 아니면 merge
         * 식별자를 직접 할당하게되면 엔티티 save 시 무조건 식별자 값이 존재하므로 merge 수행 (insert -> update)
         */
        Staff staff = staffRepository.save(requestDto.toEntity()); // 직원 저장
        // 첨부파일(사진) 저장
        try {
            attachFiles = saveFiles(files, "/staff"); // 실제 파일 저장
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
        }
        attachFiles.forEach(file -> file.addStaff(staff)); // staff_id 저장
        fileRepository.saveAll(attachFiles); // insert

        return ResponseEntity.ok(staff.getId());
    }

    /**
     * 직원 정보를 수정한다.
     * @param staffId
     * @param requestDto
     * @param files
     * @return
     */
    @Transactional
    public ResponseEntity<?> updateWithFIle(String staffId, StaffUpdateRequestDto requestDto, List<MultipartFile> files) throws NoSuchAlgorithmException {
        requestDto.encodePassword(SHA256.encrypt(requestDto.getPw())); // 비밀번호 암호화

        // 기존정보
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new NoSuchElementException("해당 직원이 없습니다. staff_id = " + staffId));
        List<AttachFile> attachFiles = staff.getAttachFiles();

        // 파일 삭제
        try {
            attachFiles.removeAll(deleteFile(attachFiles, requestDto.getRemove()));
        } catch (IOException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("파일 삭제 실패", HttpStatus.BAD_REQUEST);
        }

        // 파일 저장
        try {
            attachFiles.addAll(saveFiles(files, "/staff"));
        } catch (IOException e) {
            log.info(e.getMessage());
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
        }

        // update
        staff.update(requestDto);

        return ResponseEntity.ok(staffId);
    }

    /**
     * 신규 사번을 생성한다.
     * @param latestEmpNo 가장 최근에 등록된 사번
     * @return
     */
    public String createId (String latestEmpNo) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        int seq = 1;

        if(latestEmpNo != null && today.equals(latestEmpNo.substring(0,4))) {
            seq = Integer.parseInt(latestEmpNo.substring(4)) + 1;
        }

        return today + String.format("%05d", seq);
    }
}
