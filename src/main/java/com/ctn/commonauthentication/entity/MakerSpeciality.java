package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.dto.Maker;
import com.ctn.commonauthentication.dto.OperatorAppraisal;

import java.util.List;

public class MakerSpeciality {
    private List<Maker> contents;
    private List<Maker> maker;
    public MakerSpeciality() { }
    public boolean match(OperatorAppraisal maker) {
        if(contents.stream().anyMatch(content -> (content.getContent().equals("全て") ||(content.getContent().equals("輸入車全て") && !maker.madeFrom().isDomestic())))){
            return true;
        }
        if(contents.size() == 1){
            if (contents.get(0).blank()) return false;
        }
        return this.contents.contains(maker.maker());
    }

    public boolean match2(Maker maker) {
        if(contents.stream().anyMatch(content -> (content != null && content.getContent() != null && content.getContent().equals("全て") ) || (content != null && content.getContent() != null && content.getContent().equals("輸入車全て")))){
            return true;
        }
        if(contents.size() == 1){
            if (contents.get(0).blank()) return false;
        }
        return this.contents.contains(maker);
    }

    public boolean isEmptyMT(String buyerID) {
        if(contents.size() == 1){
            if (contents.get(0).blank()) return false;
        }
        if (this.contents.get(0).blankV2(buyerID)) {
            return false;
        }
        return true;
    }

    public List<Maker> getMaker() {
        return maker;
    }

    public void setMaker(List<Maker> maker) {
        this.maker = maker;
    }
}
