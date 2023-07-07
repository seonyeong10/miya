package jp.or.miya.domain.staff.repository;

import jp.or.miya.domain.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, String> {
    @Override
    @Query("select s from Staff s left join fetch s.attachFiles where s.id = :id")
    Optional<Staff> findById(@Param("id") String id);
    @Query(value = "SELECT id FROM Staff ORDER BY id DESC LIMIT 1")
    String findLatestId();  // 신규 직원번호 조회
}
