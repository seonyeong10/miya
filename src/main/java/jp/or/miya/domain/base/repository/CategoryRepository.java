package jp.or.miya.domain.base.repository;

import jp.or.miya.domain.base.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
