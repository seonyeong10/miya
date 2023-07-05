package jp.or.miya.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
@Slf4j
@Configuration
public class AuditConfig implements AuditorAware<String> {
    /**
     * JPA
     * @CreatedBy, @LastModifiedBy 어노테이션 설정
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            log.info("Not found authentication");
            return Optional.empty();
        }

        String staffId = authentication.getName();
        log.debug("staff_id = " + staffId);
        return Optional.of(staffId);
    }
}
