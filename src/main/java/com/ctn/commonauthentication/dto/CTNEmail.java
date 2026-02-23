package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.exception.IllegalValueException;

public class CTNEmail {
    private String content;
    private Integer emailOrder;

    private CTNEmail() {}

    public CTNEmail(String content) throws IllegalValueException {
        if (!content.matches("[\\w\\-._%+]+@[\\w\\-._]+\\.[A-Za-z]{2,}")) {
            throw new IllegalValueException();
        }
        this.content = content;
    }

    public String toString() {
        return this.content;
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
        CTNEmail other = (CTNEmail) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        return true;
    }

    public Integer getEmailOrder() {
        return emailOrder;
    }

    public void setEmailOrder(Integer emailOrder) {
        this.emailOrder = emailOrder;
    }
}
