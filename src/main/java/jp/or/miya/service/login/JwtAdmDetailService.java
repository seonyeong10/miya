package jp.or.miya.service.login;

import jp.or.miya.domain.staff.Staff;
import jp.or.miya.domain.staff.StaffRepository;
import jp.or.miya.web.dto.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAdmDetailService implements UserDetailsService {

    private final StaffRepository staffRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return staffRepository.findById(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    // User 데이터가 존재하면 UserDetails 객체로 만들어 리턴
    private UserDetails createUserDetails(Staff staff) {
//        return new User(staff.getEmpNo(), staff.getPw(), staff.getAuthorities());
        return new CustomUser(staff.getEmpNo(), staff.getPw(), staff.getAuthorities(), staff.getName());
    }
}
