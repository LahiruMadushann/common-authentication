package com.ctn.commonauthentication.util;

import com.ctn.commonauthentication.util.enums.ScheduleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ScheduleTypeConverter implements AttributeConverter<ScheduleType, String> {

    @Override
    public String convertToDatabaseColumn(ScheduleType scheduleType) {
        return scheduleType != null ? scheduleType.name() : ScheduleType.APPLY_CHANGES.name();
    }

    @Override
    public ScheduleType convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty() || "null".equals(dbData)) {
            return ScheduleType.APPLY_CHANGES;
        }
        try {
            return ScheduleType.valueOf(dbData);
        } catch (IllegalArgumentException e) {
            return ScheduleType.APPLY_CHANGES;
        }
    }
}
