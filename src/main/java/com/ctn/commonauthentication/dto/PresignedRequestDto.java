package com.ctn.commonauthentication.dto;

public class PresignedRequestDto {

    String bucketName;
    String keyName;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public PresignedRequestDto() {
    }
}

