package jp.or.miya.domain.staff;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@Entity
public class Staff extends BaseTimeEntity implements UserDetails, Persistable<String> {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Id
    @Column(name = "emp_no", nullable = false, length = 10)
    private String id; // 사번
    @Column(nullable = false, length = 10)
    private String name; // 이름
    @Column(length = 10)
    private String engName; // 영문이름
    private String dept; // 부서
    @Convert(converter = PositionConverter.class)
    private Position pos; // 직급
    @Convert(converter = ReponsibilityConverter.class)
    private Responsibility res; // 직책
    @Convert(converter = WorkConverter.class)
    private Work work; // 상태
    private String ext; // 내선번호
    private LocalDate joinDt; // 입사일
    private LocalDate resignDt; // 퇴사일
    @Enumerated(EnumType.STRING)
    private Role role; // 권한
    private String pw; // 비밀번호

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AttachFile> attachFiles = new LinkedHashSet<>();

    @Builder
    public Staff(String id, String name, String engName, String dept, Position pos, Responsibility res, Work work, String ext, LocalDate joinDt, LocalDate resignDt, Role role, String pw, Set<AttachFile> attachFiles) {
        this.id = id;
        this.name = name;
        this.engName = engName;
        this.dept = dept;
        this.pos = pos;
        this.res = res;
        this.work = work;
        this.ext = ext;
        this.joinDt = joinDt;
        this.resignDt = resignDt;
        this.role = role;
        this.pw = pw;
        this.attachFiles = attachFiles;
    }

    @Builder
    public Staff(String id, String pw, String name, Role role) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(this.role.getKey()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.pw;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Persistable
    @Override
    public boolean isNew() {
        // 등록일자가 null 이 아니면 insert
        return getRegDt() == null;
    }
}
