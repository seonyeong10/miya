package jp.or.miya.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class CustomUser extends User {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    // 추가 정보
    private String realName;

    public CustomUser(String username, String password, Collection authorities, String realName) {
        super(username, password, true, true, true, true, authorities);
        this.realName = realName;
    }
}
