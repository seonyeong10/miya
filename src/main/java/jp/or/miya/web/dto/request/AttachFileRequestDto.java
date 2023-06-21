package jp.or.miya.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AttachFileRequestDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Upload {
        private String dir;
        private Long boardId;
        private Long modEmp;

        @Builder
        public Upload(String dir, Long boardId, Long modEmp) {
            this.dir = dir;
            this.boardId = boardId;
            this.modEmp = modEmp;
        }
    }
}
