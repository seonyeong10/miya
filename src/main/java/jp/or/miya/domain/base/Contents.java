package jp.or.miya.domain.base;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Contents extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "p_id")
    private Long pId;
    @Column(name = "sort")
    private String sort;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(name = "seq")
    private Integer seq;
    @Column(name = "url")
    private String url;
    @Column(name = "acc_role")
    private String accRole;
    @Column(name = "use_yn")
    private Integer useYn;

    @Builder Contents(Long id, Long pId, String sort, String name, Integer seq, String url, String accRole, Integer useYn) {
        this.id = id;
        this.pId = pId;
        this.sort = sort;
        this.name = name;
        this.seq = seq;
        this.url = url;
        this.accRole = accRole;
        this.useYn = useYn;
    }
}
