package com.ctn.commonauthentication.dto;

import java.util.List;

public class NGMaker {
    private List<Maker> content;
    private NGMaker(){}
    public boolean contains(Maker maker) {
        return this.content.contains(maker);
    }
}
