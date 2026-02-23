package com.ctn.commonauthentication.dto;

import java.util.List;

public class WishCarType {
    private List<CarType> content;
    private List<CarType> carTypes;
    private WishCarType(){}
    public boolean contains(CarType carType) {
        return this.content.contains(carType);
    }
    public boolean containsWithMaker(CarType carType, Maker maker) {
        if(this.content.stream().anyMatch(content -> (content.getCarTypeMaker() != null && !content.getCarTypeMaker().isBlank() && !content.getCarTypeMaker().isEmpty()))){
            if(content.stream().anyMatch(content -> (content.getContent().equals("全て") ))){
                return true;
            }
            return this.content.stream().anyMatch(content -> content.getContent().equals(carType.getContent()) && content.getCarTypeMaker().equals(maker.getContent()));
        }
        return this.content.contains(carType);
    }
}
