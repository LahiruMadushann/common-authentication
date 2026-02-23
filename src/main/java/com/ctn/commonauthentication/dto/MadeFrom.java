package com.ctn.commonauthentication.dto;

public class MadeFrom {
    private boolean isDomestic;
    public MadeFrom(boolean isDomestic){
        this.isDomestic = isDomestic;
    }
    public boolean isDomestic() {
        return this.isDomestic;
    }
    public boolean isImport() {
        return !this.isDomestic;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (isDomestic ? 1231 : 1237);
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
        MadeFrom other = (MadeFrom) obj;
        if (isDomestic != other.isDomestic)
            return false;
        return true;
    }

}
