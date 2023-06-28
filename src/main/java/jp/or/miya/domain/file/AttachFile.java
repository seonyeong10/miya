package jp.or.miya.domain.file;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jp.or.miya.domain.menu.Menu;
import jp.or.miya.web.dto.request.AttachFileRequestDto;
import lombok.*;

@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Menu menu;

    @Builder
    public AttachFile (Long id, Long boardId, Menu menu, String name, String orgName, String dir) {
        this.id = id;
        this.boardId = boardId;
        this.menu = menu;
        this.name = name;
        this.orgName = orgName;
        this.dir= dir;
    }

    public AttachFile(AttachFileRequestDto.Save save) {
        this.name = save.getName();
        this.orgName = save.getOrgName();
        this.dir = save.getDir();
    }
}
