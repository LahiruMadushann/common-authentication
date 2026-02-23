package com.ctn.commonauthentication.dto;

import java.util.List;

public class NGCarType {
    private List<CarType> content;
    private List<CarType> carTypes;
    private NGCarType(){}
    public boolean contains(CarType carType) {
        if(carType.blank())return false;
        return this.content.contains(carType);
    }
    public boolean containsWithMaker(CarType carType, Maker maker) {
        if (carType.blank()) {
            return false;
        }

        if (this.content.stream().anyMatch(content -> {
            String carTypeMaker = content.getCarTypeMaker();
            return carTypeMaker != null && !carTypeMaker.isEmpty() && !carTypeMaker.isBlank();
        })) {
            if (this.content.stream().anyMatch(content -> "全て".equals(content.getCarTypeMaker()))) {
                return this.content.contains(carType);
            }
            return content.stream().anyMatch(content -> content.getContent().equals(carType.getContent()) && content.getCarTypeMaker().equals(maker.getContent()));

        }

        return this.content.contains(carType);
    }
}
