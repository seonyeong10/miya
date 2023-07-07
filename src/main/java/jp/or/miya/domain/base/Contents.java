package jp.or.miya.domain.base;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Contents extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_id")
    private Long id;
    /* 트리 구조 */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Contents parent;

    @OneToMany(mappedBy = "parent")
    private List<Contents> children = new ArrayList<>();
    /* 트리 구조 */

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

    @Builder Contents(Long id, Contents parent, List<Contents> children, String sort, String name, Integer seq, String url, String accRole, Integer useYn) {
        this.id = id;
        this.parent = parent;
        this.sort = sort;
        this.name = name;
        this.seq = seq;
        this.url = url;
        this.accRole = accRole;
        this.useYn = useYn;
        this.children = children;
    }
}
