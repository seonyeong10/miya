package jp.or.miya.service.base;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jp.or.miya.config.jwt.JwtTokenProvider;
import jp.or.miya.domain.base.Contents;
import jp.or.miya.domain.base.ContentsRepository;
import jp.or.miya.domain.base.ContentsSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentsService {
    private final ContentsRepository repository;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<?> roleContent (HttpServletRequest request) {
        Authentication authentication = jwtTokenProvider.getAuthenication(resolveToken(request));
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        String pSort = request.getParameter("sort");


        // 정렬 (Order By)
        Sort sort = Sort.by(Sort.Order.asc("id"), Sort.Order.asc("seq"));
        List<Contents> contents = repository.findAll(ContentsSpecification.equalsSortNRole(pSort, authorities), sort);

        return ResponseEntity.ok(contents);
    } // roleContent

    private String resolveToken (HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    } // resolveToken
}
