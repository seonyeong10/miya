package jp.or.miya.domain.base;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 트리 형태의 구조
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
//    @JsonBackReference
    private Category parent;
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
    @Column(length = 100)
    private String name;

    @Builder
    public Category (Category parent, String name) {
        this.parent = parent;
        this.name = name;
    }
}
