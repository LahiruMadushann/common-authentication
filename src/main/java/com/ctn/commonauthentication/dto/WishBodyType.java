package com.ctn.commonauthentication.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WishBodyType {
    private List<BodyType> content;
    private WishBodyType(){}
//    public boolean contains(BodyType bodyType) {
//        return this.content.contains(bodyType);
//    }

    public boolean acceptsAll() {
        if (this.content == null || this.content.isEmpty()) {
            return false;
        }

        return this.content.stream()
                .filter(bodyType -> bodyType != null &&
                        bodyType.getContent() != null &&
                        !bodyType.getContent().trim().isEmpty())
                .anyMatch(bodyType -> "全て".equals(bodyType.getContent().trim()));
    }

    public List<BodyType> getNonEmptyContents() {
        if (this.content == null || this.content.isEmpty()) {
            return new ArrayList<>();
        }

        return this.content.stream()
                .filter(bodyType -> bodyType != null &&
                        bodyType.getContent() != null &&
                        !bodyType.getContent().trim().isEmpty())
                .collect(Collectors.toList());
    }

    public boolean contains(BodyType bodyType) {
        if (this.content == null || this.content.isEmpty()) {
            return false;
        }
        return this.content.contains(bodyType);
    }

    public boolean containsNg(BodyType bodyType) {
        if(content.size() == 1){
            if(content.get(0).blank()) return true;
        }
        return this.content.contains(bodyType);
    }

    public boolean containsAny(List<BodyType> bodyTypes) {
        if (bodyTypes == null || bodyTypes.isEmpty()) {
            return false;
        }
        return bodyTypes.stream().anyMatch(this::contains);
    }

}
