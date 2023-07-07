package jp.or.miya.web.dto.request.staff;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jp.or.miya.domain.staff.Team;
import jp.or.miya.domain.staff.enums.Position;
import jp.or.miya.domain.staff.enums.Responsibility;
import jp.or.miya.domain.staff.enums.Work;
import jp.or.miya.domain.user.enums.Role;
import jp.or.miya.web.dto.request.AttachFileRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class StaffUpdateRequestDto {
    private String name;
    private String engName;
    private Team team;
    private Position pos;
    private Responsibility res;
    private Work work;
    private String ext;
    private Role role;
    private String pw;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    private List<AttachFileRequestDto.Save> attachFiles = new ArrayList<>(); // 등록할 첨부파일

    List<Long> remove = new ArrayList<>(); // 삭제할 첨부파일

    @Builder
    public StaffUpdateRequestDto(String name, String engName, Team team, Position pos, Responsibility res, Work work, String ext, Role role, String pw, LocalDateTime startDate, LocalDateTime endDate, List<Long> remove) {
        this.name = name;
        this.engName = engName;
        this.team = team;
        this.pos = pos;
        this.res = res;
        this.work = work;
        this.ext = ext;
        this.role = role;
        this.pw = pw;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remove = remove;
    }

    public void encodePassword (String pw) {
        this.pw = pw;
    }
}
