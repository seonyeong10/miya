package jp.or.miya.domain.staff.converter;

import jakarta.persistence.Converter;
import jp.or.miya.config.coverter.AbstractEnumAttributeConverter;
import jp.or.miya.domain.staff.enums.Work;

@Converter
public class WorkConverter extends AbstractEnumAttributeConverter<Work> {
    public WorkConverter() {
        super(Work.class);
    }
}
