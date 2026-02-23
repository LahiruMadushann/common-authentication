package com.ctn.commonauthentication.dto;

import java.util.List;

public class WishMaker {
    private List<Maker> contents;
    private WishMaker(){}
    public boolean contains(OperatorAppraisal maker) {
        if(contents.stream().anyMatch(content -> (content.getContent().equals("全て") ||(content.getContent().equals("輸入車全て") && !maker.madeFrom().isDomestic())))){
            return true;
        }
        return this.contents.contains(maker.maker());
    }
}
