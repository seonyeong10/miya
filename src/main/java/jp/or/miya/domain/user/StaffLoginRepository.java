package jp.or.miya.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffLoginRepository extends JpaRepository<StaffLogin, String> {
    @Override
    Optional<StaffLogin> findById(String empNo);
}
