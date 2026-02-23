package com.ctn.commonauthentication.dto;

public class BodyType {
    private String content;
    private String sBodyType;
    public BodyType(String content) {
        this.content = content;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        return result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BodyType other = (BodyType) obj;
        if (content == null) return false;
        if (other.content == null) return false;
        if (content == "") return false;
        if (other.content == "") return false;
        if (!content.equals(other.content)) return false;
        return true;
    }

    public boolean blank() {
        return "".equals(this.content);
    }

    public boolean blankV2(String buyerID) {
        return buyerID.equals(this.content);
    }

    public String getsBodyType() {
        return sBodyType;
    }

    public void setsBodyType(String sBodyType) {
        this.sBodyType = sBodyType;
    }
}
