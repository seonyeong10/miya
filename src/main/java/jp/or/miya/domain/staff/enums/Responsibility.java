package jp.or.miya.domain.staff.enums;

import jp.or.miya.config.coverter.EnumField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Responsibility implements EnumField {
    NONE("0", "None"),
    PART("1", "Part Leader"),
    BRANCH("2", "Branch Leader"),
    Group("3", "Group Leader"),
    DEPT("4", "Head of Department"),
    CIO("5", "CIO"),
    COO("6", "COO"),
    CEO("7", "CEO");
    private final String key;
    private final String value;
}
