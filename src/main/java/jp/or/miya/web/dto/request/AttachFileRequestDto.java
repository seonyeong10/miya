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
        private Long parentId;

        @Builder
        public Upload(String dir, Long parentId) {
            this.dir = dir;
            this.parentId = parentId;
        }
    }
}
