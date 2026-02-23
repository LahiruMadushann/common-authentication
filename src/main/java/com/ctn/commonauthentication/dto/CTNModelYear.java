package com.ctn.commonauthentication.dto;

public class CTNModelYear {
    private String expression;

    private CTNModelYear() {}
    public CTNModelYear(String expression) {
        this.expression = expression;
    }
    public int actual() {
        if (this.expression.equals("")) return 0;
        return Integer.parseInt(this.expression.replaceAll(".*\\(", "").replaceAll("\\).*", "").replaceAll("\\;", ""));
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
        CTNModelYear other = (CTNModelYear) obj;
        if (expression == null) {
            if (other.expression != null)
                return false;
        } else if (!expression.equals(other.expression))
            return false;
        return true;
    }

    public boolean unspecified() {
        return "".equals(this.expression);
    }

    public boolean specified() {
        return ! this.unspecified();
    }

    public boolean isBefore(CTNModelYear year) {
        if(this.unspecified()) return false;
        if(year.unspecified()) return false;
        return this.actual() <= year.actual();
    }

    public boolean isAfter(CTNModelYear year) {
        if(this.unspecified()) return false;
        if(year.unspecified()) return false;
        return this.actual() >= year.actual();
    }
}