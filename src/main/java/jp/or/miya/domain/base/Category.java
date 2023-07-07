package jp.or.miya.domain.base;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    /* 트리 구조 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();
    /* 트리 구조 */
    @Column(length = 100)
    private String name;

    @Builder
    public Category (Category parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    //==연관관계 메서드==//
    public void addChildren (Category child) {
        children.add(child);
        child.addParent(this);
    }

    public void addParent (Category parent) {
        this.parent = parent;
    }
}
