package com.ctn.commonauthentication.dto;

import java.util.Objects;

public class CarType {
    private String content;
    private String sCarType;
    private String carTypeMaker;
    public CarType(String content) {
        this.content = content;
    }
    //    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((content == null) ? 0 : content.hashCode());
//        return result;
//    }
    @Override
    public int hashCode() {
        return Objects.hash(content, sCarType, carTypeMaker);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public boolean equals(Object obj) {
        //modified this function
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CarType other = (CarType) obj;
        if(carTypeMaker != null && !carTypeMaker.isEmpty() && !carTypeMaker.equals("0") ) {
            return Objects.equals(carTypeMaker, other.carTypeMaker);
        }

        if(content != null && !content.isEmpty() && !content.equals("0")) {
            return Objects.equals(content, other.content);
        }
        if(sCarType != null && !sCarType.isEmpty() && !sCarType.equals("0")) {
            return Objects.equals(sCarType, other.sCarType);
        }
        return Objects.equals(content, other.content) &&
                Objects.equals(sCarType, other.sCarType) &&
                Objects.equals(carTypeMaker, other.carTypeMaker);

    }


    public boolean blank() {
        return "".equals(this.content);
    }
    public boolean blankV2(String buyerID) {
        return buyerID.equals(this.content);
    }

    public String getsCarType() {
        return sCarType;
    }

    public void setsCarType(String sCarType) {
        this.sCarType = sCarType;
    }

    public String getCarTypeMaker() {
        return carTypeMaker;
    }

    public void setCarTypeMaker(String carTypeMaker) {
        this.carTypeMaker = carTypeMaker;
    }
}
