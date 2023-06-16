package jp.or.miya.domain.base;

import org.springframework.data.jpa.domain.Specification;

// 쿼리 조건 추가
public class ContentsSpecification {
    public static Specification<Contents> equalsSortNRole(String sort, String accRole) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("sort"), sort),
                criteriaBuilder.equal(root.get("accRole"), accRole)
        );
    }
}
