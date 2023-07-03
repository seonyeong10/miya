package jp.or.miya.domain.staff;

import jakarta.persistence.Converter;
import jp.or.miya.config.coverter.AbstractEnumAttributeConverter;

@Converter
public class PositionConverter extends AbstractEnumAttributeConverter<Position> {
    public PositionConverter() {
        super(Position.class);
    }
}
