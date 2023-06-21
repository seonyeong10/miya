package jp.or.miya.domain.file;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class AttachFile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long boardId;
    @Column
    private String name;
    @Column
    private String orgName;
    @Column
    private String dir;
    @Column
    private Long modEmp;

    @Builder
    public AttachFile (Long id, Long boardId, String name, String orgName, String dir, Long modEmp) {
        this.id = id;
        this.boardId = boardId;
        this.name = name;
        this.orgName = orgName;
        this.dir= dir;
        this.modEmp = modEmp;
    }
}
