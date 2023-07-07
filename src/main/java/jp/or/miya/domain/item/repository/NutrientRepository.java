package jp.or.miya.domain.item.repository;

import jp.or.miya.domain.item.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrientRepository extends JpaRepository<Nutrient, String> {
    Nutrient findByMenuId(Long id);
}
