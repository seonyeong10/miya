package jp.or.miya.web.base;

import jakarta.servlet.http.HttpServletRequest;
import jp.or.miya.service.base.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ContentsController {
    private final ContentsService service;

    @GetMapping("/api/adm/contents")
    public ResponseEntity<?> roleContent (
            HttpServletRequest request
    ) {
        return service.roleContent(request);
    }
}
