package com.ctn.commonauthentication.entity;

import java.util.Base64;
import java.util.List;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sesv2.model.*;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import com.ctn.commonauthentication.exception.IllegalValueException;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.awscore.AwsRequest;

public class AWS {

    private AwsRequest r;

    private AWS(AwsRequest r) {
        this.r = r;
    }

    public static AWS messageRequest(AwsRequest r) {
        return new AWS(r);
    }

    public void send() throws IllegalValueException {
        if (this.r instanceof PublishRequest) {
            this.send((PublishRequest) this.r);
            return;
        }
        if (this.r instanceof SendEmailRequest) {
            this.send((SendEmailRequest) this.r);
            return;
        }
        throw new IllegalValueException();
    }

    private void send(PublishRequest r) {
        SnsClient
                .builder()
                .region(Region.AP_NORTHEAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build()
                .publish(r);
    }

    private void send(SendEmailRequest r) {
        SesV2Client.builder()
                .region(Region.AP_NORTHEAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build()
                .sendEmail(r);
    }

    public void sendJis(SendEmailRequest r) {
        try {
            // Prepare email headers and content
            String fromEmail = r.fromEmailAddress();
            String toEmail = String.join(", ", r.destination().toAddresses());
            List<String> bccAddresses = r.destination().bccAddresses();
            String subject = r.content().simple().subject().data();
            String bodyText = r.content().simple().body().text().data();

            // Construct the email with headers and body
            StringBuilder rawEmailBuilder = new StringBuilder();
            rawEmailBuilder.append("From: ").append(fromEmail).append("\r\n");
            rawEmailBuilder.append("To: ").append(toEmail).append("\r\n");

            // Add BCC addresses without encoding
            if (!bccAddresses.isEmpty()) {
                String bccHeader = String.join(", ", bccAddresses);
                rawEmailBuilder.append("Bcc: ").append(bccHeader).append("\r\n");
            }

            rawEmailBuilder.append("Subject: =?ISO-2022-JP?B?")
                    .append(Base64.getEncoder().encodeToString(subject.getBytes("ISO-2022-JP")))
                    .append("?=\r\n");
            rawEmailBuilder.append("MIME-Version: 1.0\r\n");
            rawEmailBuilder.append("Content-Type: text/plain; charset=ISO-2022-JP\r\n");
            rawEmailBuilder.append("Content-Transfer-Encoding: base64\r\n\r\n");
            rawEmailBuilder.append(Base64.getEncoder().encodeToString(bodyText.getBytes("ISO-2022-JP")));

            String rawEmail = rawEmailBuilder.toString();

            // Create SES client and send the email
            SesV2Client client = SesV2Client.builder()
                    .region(Region.AP_NORTHEAST_1)
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build();

            SendEmailRequest request = SendEmailRequest.builder()
                    .content(EmailContent.builder()
                            .raw(RawMessage.builder()
                                    .data(SdkBytes.fromByteArray(rawEmail.getBytes()))
                                    .build())
                            .build())
                    .build();

            client.sendEmail(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
