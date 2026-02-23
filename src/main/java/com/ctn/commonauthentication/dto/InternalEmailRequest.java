package com.ctn.commonauthentication.dto;

import java.util.List;

public class InternalEmailRequest {
    public String senderEmail;
    public String receiverEmail;
    public List<String> bccReceiverEmails;
    public String subject;
    public String textBody;
    public String htmlBodyBase64;
    public boolean isReceiverPredefined = false;
    public boolean isSenderPredefined = false;
    public int predefinedReceiverCode;
    public int predefinedSenderCode;

    public InternalEmailRequest() {
    }

    public InternalEmailRequest(String senderEmail, String receiverEmail, List<String> bccReceiverEmails, String subject, String textBody, String htmlBodyBase64, boolean isReceiverPredefined, boolean isSenderPredefined, int predefinedReceiverCode, int predefinedSenderCode) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.bccReceiverEmails = bccReceiverEmails;
        this.subject = subject;
        this.textBody = textBody;
        this.htmlBodyBase64 = htmlBodyBase64;
        this.isReceiverPredefined = isReceiverPredefined;
        this.isSenderPredefined = isSenderPredefined;
        this.predefinedReceiverCode = predefinedReceiverCode;
        this.predefinedSenderCode = predefinedSenderCode;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public List<String> getBccReceiverEmails() {
        return bccReceiverEmails;
    }

    public void setBccReceiverEmails(List<String> bccReceiverEmails) {
        this.bccReceiverEmails = bccReceiverEmails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getHtmlBodyBase64() {
        return htmlBodyBase64;
    }

    public void setHtmlBodyBase64(String htmlBodyBase64) {
        this.htmlBodyBase64 = htmlBodyBase64;
    }

    public boolean isReceiverPredefined() {
        return isReceiverPredefined;
    }

    public void setReceiverPredefined(boolean receiverPredefined) {
        isReceiverPredefined = receiverPredefined;
    }

    public boolean isSenderPredefined() {
        return isSenderPredefined;
    }

    public void setSenderPredefined(boolean senderPredefined) {
        isSenderPredefined = senderPredefined;
    }

    public int getPredefinedReceiverCode() {
        return predefinedReceiverCode;
    }

    public void setPredefinedReceiverCode(int predefinedReceiverCode) {
        this.predefinedReceiverCode = predefinedReceiverCode;
    }

    public int getPredefinedSenderCode() {
        return predefinedSenderCode;
    }

    public void setPredefinedSenderCode(int predefinedSenderCode) {
        this.predefinedSenderCode = predefinedSenderCode;
    }
}
