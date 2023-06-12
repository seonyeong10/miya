package jp.or.miya.domain.staff;

import jakarta.persistence.*;
import jp.or.miya.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Staff extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String empNo;

    @Column(nullable = false)
    private String name;

    @Column
    private String dept;

    @Column
    private String pos;

    @Column
    private Integer post;

    @Column
    private String addr1;

    @Column
    private String addr2;

    @Column
    private Date joinDt;

    @Column
    private Date resignDt;

    @Builder
    public Staff(String empNo, String name, String dept, String pos, Integer post, String addr1, String addr2, Date joinDt, Date resignDt) {
        this.empNo = empNo;
        this.name = name;
        this.dept = dept;
        this.pos = pos;
        this.post = post;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.joinDt = joinDt;
        this.resignDt = resignDt;
    }
}
