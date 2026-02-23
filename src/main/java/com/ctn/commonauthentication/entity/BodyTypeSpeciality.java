package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.dto.BodyType;

import java.util.List;

public class BodyTypeSpeciality {
    private List<BodyType> contents;
    private List<BodyType> bodyType;
    private BodyTypeSpeciality() { }
    public boolean match(BodyType bodyType) {
        if(contents.stream().anyMatch(content -> (content.getContent().equals("全て") ))){
            return true;
        }
        // Check if contents is empty
        if (contents.isEmpty()) {
            return false; // or handle this case as per your requirement
        }

        if(contents.size() == 1){
            if (contents.get(0).blank()) return false;
        }
        // Check if the bodyType is present in the contents
        return this.contents.contains(bodyType);
    }

    public boolean matchAny(List<BodyType> bodyTypes) {
        if(contents != null && contents.stream()
                .anyMatch(content -> content != null &&
                        content.getContent() != null &&
                        "全て".equals(content.getContent()))) {
            return true;
        }

        if (bodyTypes == null || bodyTypes.isEmpty()) {
            return false;
        }

        if (contents == null || contents.isEmpty()) {
            return false;
        }

        if(contents.size() == 1 && contents.get(0).blank()) {
            return false;
        }

        return bodyTypes.stream()
                .anyMatch(bodyType -> this.contents.contains(bodyType));
    }

    public boolean hasAll() {
        if (contents == null || contents.isEmpty()) {
            return false;
        }

        return contents.stream()
                .filter(content -> content != null &&
                        content.getContent() != null &&
                        !content.getContent().trim().isEmpty())
                .anyMatch(content -> "全て".equals(content.getContent().trim()));
    }


    public boolean matchSpecial(BodyType bodyType) {
        if(contents.stream().anyMatch(content -> (content.getsBodyType() != null && content.getsBodyType().equals("全て") ))){
            return true;
        }
        // Check if contents is empty
        if (contents.isEmpty()) {
            return false; // or handle this case as per your requirement
        }

        if(contents.size() == 1){
            if (contents.get(0).blank()) return false;
        }
        // Check if the bodyType is present in the contents
        return this.contents.contains(bodyType);
    }

    public boolean isEmptyBT(String buyerID) {
        if(contents.size() == 1){
            if (contents.get(0).blank()) return false;
        }
        if (this.contents.get(0).blankV2(buyerID)) {
            return false;
        }
        return true;
    }

    public List<BodyType> getBodyType() {
        return bodyType;
    }

    public void setBodyType(List<BodyType> bodyType) {
        this.bodyType = bodyType;
    }

    public List<BodyType> getContents() {
        return contents;
    }

    public void setContents(List<BodyType> contents) {
        this.contents = contents;
    }
}
