package com.ctn.commonauthentication.util.enums;

public enum FileUploadContentType {
    APPLICATION_JSON("application/json"),
    CSV("text/csv");

    private String value;

    FileUploadContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
