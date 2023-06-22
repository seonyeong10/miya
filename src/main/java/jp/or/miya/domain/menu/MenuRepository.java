package jp.or.miya.domain.menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, String> {
    @Override
    @EntityGraph(attributePaths = "attachFiles")
    Page<Menu> findAll(Pageable pageable);

    Page<Menu> findByPart(String part, Pageable pageable);

}
