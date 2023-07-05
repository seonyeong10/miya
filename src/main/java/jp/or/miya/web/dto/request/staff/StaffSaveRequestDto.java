package jp.or.miya.web.dto.request.staff;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jp.or.miya.domain.staff.*;
import jp.or.miya.domain.staff.enums.Position;
import jp.or.miya.domain.staff.enums.Responsibility;
import jp.or.miya.domain.staff.enums.Work;
import jp.or.miya.domain.user.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StaffSaveRequestDto {
    private String id;
    private String name;
    private String engName;
    private Team team;
    private Position pos;
    private Responsibility res;
    private Work work;
    private String ext;
    private Role role = Role.USER;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime startDate;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime endDate;
    @Builder
    public StaffSaveRequestDto (String id, String name, String engName, Team team, Position pos, Responsibility res, Work work, String ext, LocalDateTime startDate, LocalDateTime endDate, Role role) {
        this.id = id;
        this.name = name;
        this.engName = engName;
        this.team = team;
        this.pos = pos;
        this.res = res;
        this.work = work;
        this.ext = ext;
        this.startDate = startDate;
        this.endDate = endDate;
        this.role = role;
    }

    public Staff toEntity () {
        return Staff.builder()
                .id(id)
                .name(name)
                .engName(engName)
                .team(team)
                .pos(pos)
                .res(res)
                .work(work)
                .ext(ext)
                .startDate(startDate)
                .endDate(endDate)
                .role(role)
                .build();
    }
}
