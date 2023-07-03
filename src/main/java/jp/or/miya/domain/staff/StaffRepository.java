package jp.or.miya.domain.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Override
    Optional<Staff> findById(Long id);
    Optional<Staff> findByEmpNo(String empNo);
    List<Staff> findAllByOrderByIdDesc();

    @Query(value = "SELECT emp_no FROM staff ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findLatestId();  // 신규 직원번호 조회
}
