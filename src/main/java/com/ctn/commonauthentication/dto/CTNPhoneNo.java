package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.exception.IllegalValueException;

public class CTNPhoneNo {
    private String content;

    private CTNPhoneNo() {}

    public CTNPhoneNo(String content) throws IllegalValueException {
        if (!content.matches("^0[789]0\\d{8}$")) {
            throw new IllegalValueException();
        }
        this.content = content;
    }

    public String domesticFormat() {
        return this.content;
    }

    public String internationalFormat() {
        return "+81" + this.content.substring(1);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
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
        CTNPhoneNo other = (CTNPhoneNo) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        return true;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
