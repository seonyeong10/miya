package jp.or.miya.domain.staff;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import jp.or.miya.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@Entity
public class Staff extends BaseTimeEntity implements UserDetails {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Id
    @Column(nullable = false)
    private String empNo;

    @Column(nullable = false)
    private String name;

    @Column
    private String dept;

    @Column
    private String pos;

    @Column
    private String pw;

    @Column
    private Integer post;

    @Column
    private String addr1;

    @Column
    private String addr2;

    @Column
    private Date joinDt;

    @Column
    private Date resignDt;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Builder
    public Staff(String empNo, String name, String dept, String pos, String pw, Integer post, String addr1, String addr2, Date joinDt, Date resignDt, Role role) {
        this.empNo = empNo;
        this.name = name;
        this.dept = dept;
        this.pos = pos;
        this.pw = pw;
        this.post = post;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.joinDt = joinDt;
        this.resignDt = resignDt;
        this.role = role;
    }

    @Builder
    public Staff(String empNo, String pw, String name, Role role) {
        this.empNo = empNo;
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
        return this.empNo;
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
}
