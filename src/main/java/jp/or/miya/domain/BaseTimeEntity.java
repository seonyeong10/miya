package jp.or.miya.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 포함
public class BaseTimeEntity {
    @CreatedDate // Entity가 생성되어 저장될 때 자동으로 저장
    private LocalDateTime regDt;

    @LastModifiedDate // Entity의 값을 수정할 때 시간이 자동으로 저장
    private LocalDateTime modDt;
}
