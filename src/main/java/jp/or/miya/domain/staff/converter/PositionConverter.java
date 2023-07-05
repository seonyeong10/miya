package jp.or.miya.domain.staff.converter;

import jakarta.persistence.Converter;
import jp.or.miya.config.coverter.AbstractEnumAttributeConverter;
import jp.or.miya.domain.staff.enums.Position;

@Converter
public class PositionConverter extends AbstractEnumAttributeConverter<Position> {
    public PositionConverter() {
        super(Position.class);
    }
}
