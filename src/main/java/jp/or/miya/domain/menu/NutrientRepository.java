package jp.or.miya.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrientRepository extends JpaRepository<Nutrient, String> {
    Nutrient findByMenuId(Long id);
}
