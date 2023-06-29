package jp.or.miya.service.menu;

import jakarta.servlet.http.HttpServletRequest;
import jp.or.miya.config.jwt.JwtTokenProvider;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.domain.menu.MenuRepository;
import jp.or.miya.domain.menu.Nutrient;
import jp.or.miya.lib.FileUtils;
import jp.or.miya.web.dto.request.SearchRequestDto;
import jp.or.miya.web.dto.request.menu.MenuSaveRequestDto;
import jp.or.miya.web.dto.request.menu.MenuUpdateRequestDto;
import jp.or.miya.web.dto.response.menu.MenuListResponseDto;
import jp.or.miya.web.dto.response.menu.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {
    private final String BASE_DIR = "D:/03. Project/07. Miya/uploads";
    private final MenuRepository menuRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileUtils fileUtils;

    @Transactional
    public ResponseEntity<?> saveWithFile (MenuSaveRequestDto requestDto, List<MultipartFile> files, HttpServletRequest request) {
        // 수정자 등록
        requestDto.setModEmp(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)));

        // 첨부파일 저장
        Set<AttachFile> attachFiles = requestDto.getAttachFiles();
        try {
            fileUtils.saveFiles(attachFiles, files, requestDto.getDir(), null);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
        }
        /*
        for(MultipartFile f : files) {
            String orgName = f.getOriginalFilename();
            String ext = orgName.substring(orgName.lastIndexOf("."));

            AttachFile fileSave = AttachFile.builder()
                    .name(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() + ext)
                    .orgName(orgName)
                    .dir(requestDto.getDir())
                    .build();

            Path filePath = Paths.get(BASE_DIR + requestDto.getDir() + File.separator + fileSave.getName());

            // 파일 저장
            try {
                if(!Files.isDirectory(dir)) {
                    Files.createDirectories(dir);
                }

                f.transferTo(new File(filePath.toString())); // 파일 저장
                fileList.add(fileSave); // DB에 저장할 데이터 추가
            } catch (IOException e) {
                return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
            }
        } // for
         */

        // 데이터 저장
        requestDto.setAttachFiles(attachFiles);
        Menu menu = menuRepository.save(requestDto.toEntity());
        // menu_id 업데이트
        Nutrient nutrient = menu.getNutrient();
        Set<AttachFile> savedFiles = menu.getAttachFiles();

        nutrient.setMenuId(menu.getId());
        savedFiles.stream().forEach(file -> file.setMenu(menu));

        return ResponseEntity.ok(menu.getId());
    }

    @Transactional
    public ResponseEntity<?> updateWithFile (Long id, MenuUpdateRequestDto requestDto, List<MultipartFile> files, HttpServletRequest request) {
        // 수정자 등록
        requestDto.setModEmp(jwtTokenProvider.getUserId(jwtTokenProvider.resolveToken(request)));

        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id = " + id));
        // List.copyOf remove 호출하면 UnsupportedOperationException 반환
        Set<AttachFile> attachFiles = menu.getAttachFiles(); // 기존 첨부파일
        Nutrient nutrient = menu.getNutrient(); // 기존 영양정보

        // 기존 파일 삭제
        try {
            fileUtils.deleteFile(attachFiles, requestDto.getRemove());
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 삭제 실패", HttpStatus.BAD_REQUEST);
        }
        /*
        for(Long i : requestDto.getRemove()) {
            AttachFile removeFile = attachFiles.stream().filter(file -> file.getId() == i).findAny().orElse(null);

            // 파일 삭제
            Path path = Paths.get(BASE_DIR + removeFile.getDir() + File.separator + removeFile.getName());
            try {
                Files.deleteIfExists(path);
                attachFiles.remove(removeFile);
            } catch (IOException e) {
                log.error(e.getMessage());
                return new ResponseEntity<>("파일 삭제 실패", HttpStatus.BAD_REQUEST);
            }
        } // for
         */

        // 신규 파일 저장
        try {
            fileUtils.saveFiles(attachFiles, files, requestDto.getDir(), menu);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
        }
        /*
        for(MultipartFile f : files) {
            String orgName = f.getOriginalFilename();
            String ext = orgName.substring(orgName.lastIndexOf("."));

            AttachFile fileSave = AttachFile.builder()
                    .name(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() + ext)
                    .orgName(orgName)
                    .dir(requestDto.getDir())
                    .menu(menu)
                    .build();

            Path filePath = Paths.get(BASE_DIR + requestDto.getDir() + File.separator + fileSave.getName());

            // 파일 저장
            try {
                if(!Files.isDirectory(dir)) {
                    Files.createDirectories(dir);
                }

                f.transferTo(new File(filePath.toString())); // 파일 저장
                attachFiles.add(fileSave); // DB에 저장할 데이터 추가
            } catch (IOException e) {
                log.error(e.getMessage());
                return new ResponseEntity<>("파일 업로드 실패", HttpStatus.BAD_REQUEST);
            }
        } // for
         */

        // Update
        menu.update(requestDto);
        nutrient.update(requestDto);

        return ResponseEntity.ok(menu.getId());
    }

    public List<MenuListResponseDto> findAll (SearchRequestDto requestDto) {
        PageRequest page = PageRequest.of(requestDto.getPage(), requestDto.getPage() + 10, Sort.by(Sort.Direction.DESC, "id"));

        return menuRepository.findAll(page).stream()
                .map(MenuListResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<MenuListResponseDto> findPart (String part, SearchRequestDto requestDto) {
        PageRequest page = PageRequest.of(requestDto.getPage(), requestDto.getPage() + 10, Sort.by(Sort.Direction.DESC, "id"));

        return menuRepository.findByPart(part, page).stream()
                .map(MenuListResponseDto::new)
                .collect(Collectors.toList());
    }

    public MenuResponseDto findOne (Long id) {
        Menu entity = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id = " + id));

        return new MenuResponseDto(entity);
    }

    public void delete (Long id) {
        Menu entity = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id = " + id));
        menuRepository.delete(entity);
    }
}
