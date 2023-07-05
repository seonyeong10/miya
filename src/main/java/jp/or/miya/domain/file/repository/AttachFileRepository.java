package jp.or.miya.domain.file.repository;

import jp.or.miya.domain.file.AttachFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachFileRepository extends JpaRepository<AttachFile, Long> {
}
