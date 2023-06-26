package jp.or.miya.web.dto.response.file;

import jp.or.miya.domain.file.AttachFile;
import lombok.Getter;

@Getter
public class FileResponseDto {
    private Long id;
    private String name;
    private String orgName;
    private String dir;

    public FileResponseDto (AttachFile entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.orgName = entity.getOrgName();
        this.dir = entity.getDir();
    }
}
