package jp.or.miya.domain.staff;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, String> {
    @Override
    Optional<Staff> findById(String empNo);
}
