package jp.or.miya.domain.staff.converter;

import jakarta.persistence.Converter;
import jp.or.miya.config.coverter.AbstractEnumAttributeConverter;
import jp.or.miya.domain.staff.enums.Responsibility;

@Converter
public class ReponsibilityConverter extends AbstractEnumAttributeConverter<Responsibility> {
    public ReponsibilityConverter () {
        super(Responsibility.class);
    }
}
