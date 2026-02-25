package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.dto.InternalEmailRequest;
import com.ctn.commonauthentication.dto.OperatorAppraisal;
import jakarta.mail.internet.AddressException;

import java.io.UnsupportedEncodingException;

public interface IInternalEmailService {
    public void sendEmail(InternalEmailRequest request);

    public void sendAppraisalAmountEntryEmail(String receiverEmail, String endUserName)
            throws UnsupportedEncodingException, AddressException;

    public void sendReviewEntryEmail(String receiverEmail, String endUserName)
            throws UnsupportedEncodingException, AddressException;

    public void sendNewMessageNotificationEmail(String receiverEmail)
            throws UnsupportedEncodingException, AddressException;

    public void sendPhotoInformationUploadEmail(String receiverEmail, OperatorAppraisal appraisal, String name)
            throws UnsupportedEncodingException, AddressException;

    public void sendPasswordResetEmail(String email, String token, Integer userId);

}
