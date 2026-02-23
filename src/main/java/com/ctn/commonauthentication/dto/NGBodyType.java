package com.ctn.commonauthentication.dto;

import java.util.List;

public class NGBodyType {
    private List<BodyType> content;
    private NGBodyType(){}
    public boolean contains(BodyType bodyType) {
        if(bodyType.blank())return false;
        return this.content.contains(bodyType);
    }

    public boolean containsAny(List<BodyType> bodyTypes) {
        if (bodyTypes == null || bodyTypes.isEmpty()) {
            return false;
        }
        return bodyTypes.stream().anyMatch(this::contains);
    }
}
