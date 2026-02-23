package com.ctn.commonauthentication.entity;

public class ShopID {
    private int content;
    //add assessedEx
    private String assessedEx;

    public ShopID(int content) {
        this.content = content;
    }
    //add new
    public ShopID(String content) {
        this.content = Integer.parseInt(content);
    }
    //change here
    public ShopID(int content, String assessedEx) {
        this.content = content;
        this.assessedEx = assessedEx;
    }

    public ShopID() {
        this.content = -1;
    }

    public int actual() {
        return this.content;
    }

    public boolean equals(ShopID other) {
        return this.content == other.content;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + content;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShopID other = (ShopID) obj;
        if (content != other.content)
            return false;
        return true;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public String getAssessedEx() {
        return assessedEx;
    }

    public void setAssessedEx(String assessedEx) {
        this.assessedEx = assessedEx;
    }
}
