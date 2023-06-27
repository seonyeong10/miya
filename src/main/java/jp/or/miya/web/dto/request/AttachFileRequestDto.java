package jp.or.miya.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AttachFileRequestDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Upload {
        private String dir;
        private Long parentId;

        @Builder
        public Upload (String dir, Long parentId) {
            this.dir = dir;
            this.parentId = parentId;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Delete {
        private ArrayList<Long> remove = new ArrayList<>();

        @Builder
        public Delete (ArrayList<Long> remove) {
            this.remove = remove;
        }
    }
}
