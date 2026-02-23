package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.dto.CarType;
import com.ctn.commonauthentication.dto.Maker;

import java.util.List;

public class CarTypeSpeciality {
    private List<CarType> contents;
    private List<CarType> carTypes;

    public CarTypeSpeciality() { }

    public boolean match(CarType carType) {
        if(contents.stream().anyMatch(content -> (content.getContent().equals("全て") ))){
            return true;
        }
        if(contents.size() == 1){
            if (contents.get(0).blank()) return false;
        }
        return this.contents.contains(carType);
    }

    public boolean matchCarTypeWithMaker(CarType carType, Maker maker) {
        if(contents.stream().anyMatch(content -> (content.getContent().equals("全て") ))){
            return true;
        }

        if(contents.size() == 1){
            if (contents.get(0).blank()) return false;
        }
        if (contents.stream().allMatch(content -> (content.getCarTypeMaker() == null || content.getCarTypeMaker().isEmpty() || content.getCarTypeMaker().isBlank()))){
            return this.contents.contains(carType);
        } else {
            if (contents.stream().anyMatch(content -> (content.getCarTypeMaker().equals("全て") ))){
                return this.contents.contains(carType);
            }
            return this.contents.stream()
                    .anyMatch(content -> content.getContent().equals(carType.getContent()) &&
                            content.getCarTypeMaker().equals(maker.getContent()));
        }

    }

    public boolean matchSpecial(CarType carType) {
        if(contents.stream().anyMatch(content -> (content.getsCarType() != null && content.getsCarType().equals("全て") ))){
            return true;
        }
        if(contents.size() == 1){
            if (contents.get(0).blank()) return false;
        }
        return this.contents.contains(carType);
    }

    public boolean isEmptyCT(String buyerID) {
        if(contents.size() == 1){
            if (contents.get(0).blank()) return false;
        }
        if (this.contents.get(0).blankV2(buyerID)) {
            return false;
        }
        return true;
    }

    public List<CarType> getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(List<CarType> carTypes) {
        this.carTypes = carTypes;
    }
}
