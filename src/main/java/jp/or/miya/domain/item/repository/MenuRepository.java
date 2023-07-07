package jp.or.miya.domain.item.repository;

import jp.or.miya.domain.item.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryCustom {
    @Query("select m from Menu m left join fetch m.category left join fetch m.nutrient left join fetch m.attachFiles where m.id = :id")
    @Override
    Optional<Menu> findById(@Param("id") Long aLong);
}
