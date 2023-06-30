package jp.or.miya.domain.menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Page<Menu> findAllByOrderByIdDesc(Pageable pageable);

    Page<Menu> findByPartOrderByIdDesc(String part, Pageable pageable);

    @Query(value = "select m.* from menu m where (m.name like concat('%', :keyword, '%') or m.eng_name like concat('%', :keyword, '%')) and m.category in :category order by m.id desc", nativeQuery = true)
    Page<Menu> findAllByKeyword(@Param("keyword") String keyword, @Param("category") List<String> category, Pageable pageable);
}
