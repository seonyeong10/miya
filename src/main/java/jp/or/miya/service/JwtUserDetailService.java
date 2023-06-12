package jp.or.miya.service;

import jp.or.miya.domain.user.StaffLogin;
import jp.or.miya.domain.user.StaffLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {

    private final StaffLoginRepository staffLoginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return staffLoginRepository.findById(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    // User 데이터가 존재하면 UserDetails 객체로 만들어 리턴
    private UserDetails createUserDetails(StaffLogin staffLogin) {
        return new User(staffLogin.getEmpNo(), staffLogin.getPw(), staffLogin.getAuthorities());
    }
}
