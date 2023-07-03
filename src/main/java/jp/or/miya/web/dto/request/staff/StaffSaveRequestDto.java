package jp.or.miya.web.dto.request.staff;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jp.or.miya.domain.file.AttachFile;
import jp.or.miya.domain.staff.Position;
import jp.or.miya.domain.staff.Responsibility;
import jp.or.miya.domain.staff.Staff;
import jp.or.miya.domain.staff.Work;
import jp.or.miya.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class StaffSaveRequestDto {
    private String empNo;
    private String name;
    private String engName;
    private String dept;
    private Position pos;
    private Responsibility res;
    private Work work;
    private String ext;
    private Role role = Role.USER;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate joinDt;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate resignDt;
    @Builder
    public StaffSaveRequestDto (String empNo, String name, String engName, String dept, Position pos, Responsibility res, Work work, String ext, LocalDate joinDt, LocalDate resignDt, Role role) {
        this.empNo = empNo;
        this.name = name;
        this.engName = engName;
        this.dept = dept;
        this.pos = pos;
        this.res = res;
        this.work = work;
        this.ext = ext;
        this.joinDt = joinDt;
        this.resignDt = resignDt;
        this.role = role;
    }

    public Staff toEntity (Set<AttachFile> attachFiles) {
        return Staff.builder()
                .empNo(empNo)
                .name(name)
                .engName(engName)
                .dept(dept)
                .pos(pos)
                .res(res)
                .work(work)
                .ext(ext)
                .joinDt(joinDt)
                .resignDt(resignDt)
                .role(role)
                .attachFiles(attachFiles)
                .build();
    }
}
