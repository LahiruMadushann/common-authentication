package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.dto.ImmovableOkPatternDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pattern")
public class Pattern {
    @Id
    private PatternKey patternKey;

    public ImmovableOkPatternDto toImmovableOkPatternDto() {
        ImmovableOkPatternDto immovableOkPatternDto = new ImmovableOkPatternDto();
        immovableOkPatternDto.setAccidentHistory(patternKey.getAccidentHistory());
        immovableOkPatternDto.setRunnable(patternKey.getRunnable());
        return immovableOkPatternDto;
    }
}
