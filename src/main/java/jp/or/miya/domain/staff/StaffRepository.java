package jp.or.miya.domain.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, String> {
    @Override
    Optional<Staff> findById(String id);
    @Query(value = "SELECT emp_no FROM staff ORDER BY emp_no DESC LIMIT 1", nativeQuery = true)
    String findLatestId();  // 신규 직원번호 조회
}
