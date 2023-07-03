package jp.or.miya.config.coverter;

import jakarta.persistence.AttributeConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AbstractEnumAttributeConverter<T extends Enum<T> & EnumField> implements AttributeConverter<T, String> {
    private final Class<T> targetEnumClass;

    /**
     * Entity 의 Enum 을 매핑되는 DB 컬럼 (Code) 으로 변환
     * @param attribute  the entity attribute value to be converted
     * @return
     */
    @Override
    public String convertToDatabaseColumn(T attribute) {
        return EnumUtils.toDBCode(attribute);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return EnumUtils.toEntityCode(targetEnumClass, dbData);
    }
}
