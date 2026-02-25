package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.util.enums.FileUploadContentType;

public interface IS3Services {

    public String getPreSignedUrl(String bucketName, String keyName);
    public String getPreSignedPutUrl(String bucketName, String keyName);
    // upload file as byte array
    public void uploadFile(String bucketName, String keyName, byte[] fileBytes , FileUploadContentType contentType) throws Exception;

}
