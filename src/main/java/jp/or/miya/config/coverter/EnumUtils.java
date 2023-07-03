package jp.or.miya.config.coverter;

import lombok.NoArgsConstructor;

import java.util.EnumSet;
import java.util.NoSuchElementException;

@NoArgsConstructor
public final class EnumUtils { // final Class : 상속 불가능
    public static <T extends Enum<T> & EnumField> String toDBCode(T enumClass) {
        if(enumClass == null) {
            return "";
        }
        return enumClass.getKey();
    }

    public static <T extends Enum<T> & EnumField> T toEntityCode(Class<T> enumClass, String dbCode) {
        if(dbCode.isEmpty() || dbCode == null) {
            return null;
        }
        return EnumSet.allOf(enumClass).stream()
                .filter(c -> c.getKey().equals(dbCode))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("enum=[%s], code=[%s]는 존재하지 않는 코드입니다.", enumClass.getName(), dbCode)));
    }
}
