package com.ctn.commonauthentication.dto;

public class District {
    private String postNo;
    private String prefecture;
    private String municipalities;
    private boolean wholeArea;

    private District(){}

    public District(String postNo, String prefecture, String municipalities, boolean wholeArea) {
        this.postNo = postNo;
        this.prefecture = prefecture;
        this.municipalities = municipalities;
        this.wholeArea = wholeArea;
    }

    public boolean in(District target) {
        if (this.prefecture.equals("全国")) return true;
        if (this.prefecture.equals(target.prefecture)) {
            if (this.municipalities.equals(target.municipalities)) return true;
            if (target.municipalities.contains(this.municipalities) || this.municipalities.contains(target.municipalities)) return true;
            if (this.municipalities.equals("全域" )) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((postNo == null) ? 0 : postNo.hashCode());
        result = prime * result + ((prefecture == null) ? 0 : prefecture.hashCode());
        result = prime * result + ((municipalities == null) ? 0 : municipalities.hashCode());
        result = prime * result + (wholeArea ? 1231 : 1237);
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
        District other = (District) obj;
        if (postNo == null) {
            if (other.postNo != null)
                return false;
        } else if (!postNo.equals(other.postNo))
            return false;
        if (prefecture == null) {
            if (other.prefecture != null)
                return false;
        } else if (!prefecture.equals(other.prefecture))
            return false;
        if (municipalities == null) {
            if (other.municipalities != null)
                return false;
        } else if (!municipalities.equals(other.municipalities))
            return false;
        if (wholeArea != other.wholeArea)
            return false;
        return true;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(String municipalities) {
        this.municipalities = municipalities;
    }

    public boolean isWholeArea() {
        return wholeArea;
    }

    public void setWholeArea(boolean wholeArea) {
        this.wholeArea = wholeArea;
    }
}
