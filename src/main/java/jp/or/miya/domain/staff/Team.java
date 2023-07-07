package jp.or.miya.domain.staff;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Team extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;
    @Column(name = "parent_id")
    private Long parentId; // 상위 부서 id
    @Column(length = 100)
    private String name; // 팀 이름
    private int sort; // 정렬 순서

    @Builder
    public Team (Long parentId, String name, int sort) {
        this.parentId = parentId;
        this.name = name;
        this.sort = sort;
    }
}
