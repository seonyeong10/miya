package jp.or.miya.domain.staff;

import jakarta.persistence.Converter;
import jp.or.miya.config.coverter.AbstractEnumAttributeConverter;

@Converter
public class WorkConverter extends AbstractEnumAttributeConverter<Work> {
    public WorkConverter() {
        super(Work.class);
    }
}
