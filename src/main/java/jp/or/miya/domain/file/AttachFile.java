package jp.or.miya.domain.file;

import jakarta.persistence.*;
import jp.or.miya.domain.item.Item;
import jp.or.miya.domain.item.Menu;
import jp.or.miya.domain.staff.Staff;
import jp.or.miya.web.dto.request.AttachFileRequestDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AttachFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attach_file_id")
    private Long id;
    @Column
    private String name;
    @Column
    private String orgName;
    @Column
    private String dir;
    private int seq; // 순서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Builder
    public AttachFile (Long id, Item item, String name, String orgName, String dir, int seq) {
        this.id = id;
        this.item = item;
        this.name = name;
        this.orgName = orgName;
        this.dir= dir;
        this.seq = seq;
    }

    public AttachFile(AttachFileRequestDto.Save save) {
        this.name = save.getName();
        this.orgName = save.getOrgName();
        this.dir = save.getDir();
    }

    /**
     * 직원을 저장한다.
     * @param staff
     */
    public void addStaff (Staff staff) {
        this.staff = staff;
        staff.getAttachFiles().add(this);
    }

    /**
     * 메뉴를 저장한다.
     * @param item
     */
    public void addItem (Item item) {
        this.item = item;
        item.getAttachFiles().add(this);
    }

    /**
     * seq 값을 저장한다.
     * @param seq
     */
    public void addSeq (int seq) {
        this.seq = seq;
    }
}
