package com.ctn.commonauthentication.serviceImpl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.ctn.commonauthentication.dto.InternalEmailRequest;
import com.ctn.commonauthentication.dto.OperatorAppraisal;
import com.ctn.commonauthentication.service.IInternalEmailService;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

@Service
public class EmailService implements IInternalEmailService {

    private static final String BUYER_PORTAL_URL = "https://development.da9f2us1k1n15.amplifyapp.com";
    private static final String SELLER_PORTAL_URL = "https://development.da9f2us1k1n15.amplifyapp.com";

    @Override
    public void sendAppraisalAmountEntryEmail(String receiverEmail, String endUserName) throws UnsupportedEncodingException, AddressException {
        InternalEmailRequest emailRequest = new InternalEmailRequest();
        emailRequest.senderEmail = new InternetAddress(PredefinedSenders.ADMIN_SENDER_EMAIL, "CTN車一括査定", "UTF-8").toUnicodeString();
        emailRequest.receiverEmail = receiverEmail;
        emailRequest.subject = "【 CTN 】査定額入力のお知らせ";
        emailRequest.htmlBodyBase64 = buildAppraisalAmountEntryEmail(endUserName);
        emailRequest.textBody = buildAppraisalAmountEntryEmail(endUserName);
        sendEmail(emailRequest);
    }


    @Override
    public void sendReviewEntryEmail(String receiverEmail, String endUserName) throws UnsupportedEncodingException, AddressException {
        InternalEmailRequest emailRequest = new InternalEmailRequest();
        emailRequest.bccReceiverEmails = Arrays.asList(PredefinedReceivers.ADMIN_BCC_RECEIVER_EMAIL);
        emailRequest.senderEmail = new InternetAddress(PredefinedSenders.ADMIN_SENDER_EMAIL, "CTN車一括査定", "UTF-8").toUnicodeString();
        emailRequest.receiverEmail = receiverEmail;
        emailRequest.subject = endUserName + "様 レビュー入力のお知らせ";
        emailRequest.htmlBodyBase64 = buildReviewEntryEmail(endUserName);
        emailRequest.textBody = buildReviewEntryEmail(endUserName);
        sendEmail(emailRequest);
    }

    @Override
    public void sendNewMessageNotificationEmail(String receiverEmail) throws UnsupportedEncodingException, AddressException {
        InternalEmailRequest emailRequest = new InternalEmailRequest();
        emailRequest.senderEmail = new InternetAddress(PredefinedSenders.ADMIN_SENDER_EMAIL, "CTN車一括査定", "UTF-8").toUnicodeString();
        emailRequest.receiverEmail = receiverEmail;
        emailRequest.subject = "CTN車一括査定 - 新しいメッセージのお知らせ";
        emailRequest.htmlBodyBase64 = buildNewMessageNotificationEmail();
        emailRequest.textBody = buildNewMessageNotificationEmail();
        sendEmail(emailRequest);
    }

    @Override
    public void sendPhotoInformationUploadEmail(String receiverEmail, OperatorAppraisal appraisal, String name) throws UnsupportedEncodingException, AddressException {
        InternalEmailRequest emailRequest = new InternalEmailRequest();
        emailRequest.senderEmail = new InternetAddress(PredefinedSenders.ADMIN_SENDER_EMAIL, "CTN車一括査定", "UTF-8").toUnicodeString();
        emailRequest.receiverEmail = receiverEmail;
        emailRequest.subject = "写真情報アップロードのお知らせ";
        emailRequest.htmlBodyBase64 = buildPhotoInformationUploadEmail(appraisal, name);
        emailRequest.textBody = buildPhotoInformationUploadEmail(appraisal, name);
        sendEmail(emailRequest);
    }

    private String buildReviewEntryEmail(String endUserName) {
        StringBuilder htmlBody = new StringBuilder();
        htmlBody.append("お世話になっております。<br>")
                .append("CTN車一括査定です。<br><br>")
                .append("下記").append(endUserName).append("様よりレビューが入力されましたことをお知らせいたします。<br><br>")
                .append("【" + BUYER_PORTAL_URL + "】<br>");
        return htmlBody.toString();
    }

    private String buildAppraisalAmountEntryEmail(String endUserName) {
        StringBuilder htmlBody = new StringBuilder();
        htmlBody.append(endUserName).append("様<br>")
                .append("CTN車一括査定でございます。<br><br>")
                .append("この度、査定依頼に対し、査定額が入力されましたのでお知らせいたします。<br>")
                .append(BUYER_PORTAL_URL + "<br>")
                .append("ご不明な点がございましたら、いつでもお気軽にお問い合わせくださいませ。<br>")
                .append("引き続き、CTN車一括査定をどうぞよろしくお願い申し上げます。<br>");
        return htmlBody.toString();
    }

    @Override
    public void sendEmail(InternalEmailRequest request) {

        if (request.isSenderPredefined) {
            request.senderEmail = PredefinedSenders.ADMIN_SENDER_EMAIL;
        }
        if (request.isReceiverPredefined) {
            request.receiverEmail = PredefinedReceivers.ADMIN_RECEIVER_EMAIL;
        }

        List<String> bccAddresses = request.bccReceiverEmails;

        // Construct Destination with BCC address only if not null
        Destination destination = new Destination().withToAddresses(request.receiverEmail);
        if (bccAddresses != null && !bccAddresses.isEmpty()) {
            destination = destination.withBccAddresses(bccAddresses);
        }

        //String decodedHtmlBody = new String(java.util.Base64.getDecoder().decode(request.htmlBodyBase64));
        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        .withRegion(Regions.AP_NORTHEAST_1).build();

        SendEmailRequest finalizedEmailRequest = new SendEmailRequest()
                .withDestination(destination).withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset("UTF-8").withData(request.htmlBodyBase64))
                                .withText(new Content()
                                        .withCharset("UTF-8").withData(request.textBody)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(request.subject)))
                .withSource(request.senderEmail);
        client.sendEmail(finalizedEmailRequest);
    }

    private String buildNewMessageNotificationEmail() {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<!DOCTYPE html>\n")
                .append("<html lang=\"ja\">\n")
                .append("<head>\n")
                .append("    <meta charset=\"UTF-8\">\n")
                .append("    <title>新しいメッセージのお知らせ</title>\n")
                .append("</head>\n")
                .append("<body>\n")
                .append("    <p>お世話になっております。<br>\n")
                .append("    CTN車一括査定です。</p>\n")
                .append("\n")
                .append("    <p>新しいメッセージが届いております。<br>\n")
                .append("    下記リンクよりログインして、メッセージ内容をご確認ください。</p>\n")
                .append("\n")
                .append("    <p><a href=\"").append(BUYER_PORTAL_URL).append("\">ログインURL</a></p>\n")
                .append("\n")
                .append("    <p>今後ともよろしくお願いいたします。<br>\n")
                .append("    CTN車一括査定</p>\n")
                .append("</body>\n")
                .append("</html>");
        return emailContent.toString();
    }

    private String buildPhotoInformationUploadEmail(OperatorAppraisal appraisal, String name) {
        StringBuilder htmlBody = new StringBuilder();
        htmlBody.append("お世話になっております。<br>")
                .append("CTN車一括査定です。<br><br>")
                .append(name).append("様の車両写真情報がアップロードされましたことをお知らせいたします。<br>")
                .append(BUYER_PORTAL_URL +  "<br><br>")
                .append("ご不明な点がございましたら、お気軽にお問い合わせくださいませ。<br>")
                .append("今後とも、CTN車一括査定をよろしくお願い申し上げます。<br>");
        return htmlBody.toString();
    }
}
