package jp.or.miya.domain.staff;

import jakarta.persistence.Converter;
import jp.or.miya.config.coverter.AbstractEnumAttributeConverter;

@Converter
public class ReponsibilityConverter extends AbstractEnumAttributeConverter<Responsibility> {
    public ReponsibilityConverter () {
        super(Responsibility.class);
    }
}
