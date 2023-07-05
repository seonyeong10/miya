package jp.or.miya.domain.base.repository;

import jp.or.miya.domain.base.Contents;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, String>, JpaSpecificationExecutor<Contents> {
    @Override
    List<Contents> findAll(Specification<Contents> spec, Sort sort);
}
