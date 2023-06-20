package jp.or.miya.domain.menu;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class AttachFile {
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

    @Builder
    public AttachFile (Long id, Long boardId, String name, String orgName, String dir) {
        this.id = id;
        this.boardId = boardId;
        this.name = name;
        this.orgName = orgName;
        this.dir= dir;
    }
}
