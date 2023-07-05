package jp.or.miya.domain.menu.repository;

import jp.or.miya.domain.menu.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrientRepository extends JpaRepository<Nutrient, String> {
    Nutrient findByMenuId(Long id);
}
