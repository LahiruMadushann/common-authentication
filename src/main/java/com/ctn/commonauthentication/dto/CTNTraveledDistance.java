package com.ctn.commonauthentication.dto;

public class CTNTraveledDistance {
    private String expression;
    private CTNTraveledDistance() {}

    public CTNTraveledDistance(String expression) {
        this.expression = expression;
    }

    public int actual() {
        return "".equals(this.expression) ? 0 : Integer.parseInt(this.expression.replace("～", "").replace("km", "").replace(",", ""));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expression == null) ? 0 : expression.hashCode());
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
        CTNTraveledDistance other = (CTNTraveledDistance) obj;
        if (expression == null) {
            if (other.expression != null)
                return false;
        } else if (!expression.equals(other.expression))
            return false;
        return true;
    }

    public boolean unspecified() {
        return "".equals(this.expression) || "不明".equals(this.expression) ;
    }

    public boolean specified() {
        return ! this.unspecified();
    }

    public boolean isBefore(CTNTraveledDistance year) {
        if(this.unspecified()) return false;
        if(year.unspecified()) return false;
        return this.actual() <= year.actual();
    }

    public boolean isAfter(CTNTraveledDistance year) {
        if(this.unspecified()) return false;
        if(year.unspecified()) return false;
        return this.actual() >= year.actual();
    }

}
