package jp.or.miya.domain.staff;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import jp.or.miya.domain.Period;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.staff.converter.PositionConverter;
import jp.or.miya.domain.staff.converter.ReponsibilityConverter;
import jp.or.miya.domain.staff.converter.WorkConverter;
import jp.or.miya.domain.staff.enums.Position;
import jp.or.miya.domain.staff.enums.Responsibility;
import jp.or.miya.domain.staff.enums.Work;
import jp.or.miya.domain.user.enums.Role;
import jp.or.miya.web.dto.request.staff.StaffUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Staff extends BaseTimeEntity implements UserDetails, Persistable<String> {
    @Id
    @Column(name = "staff_id", nullable = false, length = 10)
    private String id; // 사번
    @Column(nullable = false, length = 10)
    private String name; // 이름
    @Column(length = 10)
    private String engName; // 영문이름
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    @Convert(converter = PositionConverter.class)
    private Position pos; // 직급
    @Convert(converter = ReponsibilityConverter.class)
    private Responsibility res; // 직책
    @Convert(converter = WorkConverter.class)
    private Work work; // 상태
    private String ext; // 내선번호
    @Embedded
    private Period period; // 근무 기간
    @Enumerated(EnumType.STRING)
    private Role role; // 권한
    private String pw; // 비밀번호
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachFile> attachFiles = new ArrayList<>();

    @Builder
    public Staff(String id, String name, String engName, Team team, Position pos, Responsibility res, Work work, String ext, LocalDateTime startDate, LocalDateTime endDate, Role role, String pw) {
        this.id = id;
        this.name = name;
        this.engName = engName;
        this.team = team;
        this.pos = pos;
        this.res = res;
        this.work = work;
        this.ext = ext;
        this.period = Period.builder().startDate(startDate).endDate(endDate).build();
        this.role = role;
        this.pw = pw;
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
        return getCreatedDate() == null;
    }

    public void addPassword (String pw) {
        this.pw = pw;
    }

    /* 비지니스 메서드 */
    public void update(StaffUpdateRequestDto dto) {
        this.name = dto.getName();
        this.engName = dto.getEngName();
        this.team = dto.getTeam();
        this.pos = dto.getPos();
        this.res = dto.getRes();
        this.work = dto.getWork();
        this.ext = dto.getExt();
        this.role = dto.getRole();
        this.pw = dto.getPw();
    }
}
