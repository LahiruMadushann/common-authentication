package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.exception.IllegalSizeException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AssessmentDateList {

    private List<CTNDateTime> content;
    private static int MAX_APPRAISAL_PREFERRED_DAY_LIST = 3;

    private AssessmentDateList(){}

    public AssessmentDateList(String... content) throws IllegalSizeException {
        if (overSize(content.length)) throw new IllegalSizeException();
        this.content = Stream.of(content).map(e -> new CTNDateTime(e)).toList();
    }
    public AssessmentDateList(List<CTNDateTime> content) throws IllegalSizeException {
        if (overSize(content.size())) throw new IllegalSizeException();
        this.content = content;
    }

    public AssessmentDateList(CTNDateTime... content) throws IllegalSizeException {
        if (overSize(content.length)) throw new IllegalSizeException();
        this.content = new ArrayList<>();
        for(CTNDateTime d : content) {
            this.content.add(d);
        }
    }

    public boolean hasValue() {
        return this.content.stream().filter(e -> e.filled()).toList().size() > 0;
    }

    private static boolean overSize(int size) {
        return size > MAX_APPRAISAL_PREFERRED_DAY_LIST;
    }

    public CTNDateTime getFirstDate() {
        return content != null && !content.isEmpty() ? content.get(0) : null;
    }

}
