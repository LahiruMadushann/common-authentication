package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.entity.Pattern;
import com.ctn.commonauthentication.entity.PatternKey;
import lombok.Data;

@Data
public class ImmovableOkPatternDto {
    private String accidentHistory;
    private String runnable;

    public Pattern toPattern(int shopId) {
        Pattern pattern = new Pattern();
        PatternKey patternKey = new PatternKey();
        patternKey.setShopId(shopId);
        patternKey.setAccidentHistory(accidentHistory);
        patternKey.setRunnable(runnable);
        pattern.setPatternKey(patternKey);
        return pattern;
    }
}
