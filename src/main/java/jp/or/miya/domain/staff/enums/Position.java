package jp.or.miya.domain.staff.enums;

import jp.or.miya.config.coverter.EnumField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Position implements EnumField {
    PART("1", "Part timer"),
    INTERN("2", "Intern"),
    ASSISTANT("3", "Assistant"),
    PROF("4", "Professional"),
    ASSISTMGR("5", "Assistant Manager"),
    GENERALMGR("6", "General Manager"),
    DEPTMGR("7", "Department Manager"),
    DRIECTOR("8", "Director"),
    VICE("9", "Vice President"),
    CHAIRMAN("10", "Chairman")
    ;
    private final String key;
    private final String value;
}
