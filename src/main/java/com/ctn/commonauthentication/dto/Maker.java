package com.ctn.commonauthentication.dto;

public class Maker {
    private String content;
    private String sMaker;
    public Maker(String content) {
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
        Maker other = (Maker) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        return true;
    }

    public boolean blank() {
        return "".equals(this.content);
    }

    public boolean blankV2(String buyerID) {
        return buyerID.equals(this.content);
    }

    public String getsMaker() {
        return sMaker;
    }

    public void setsMaker(String sMaker) {
        this.sMaker = sMaker;
    }
}
