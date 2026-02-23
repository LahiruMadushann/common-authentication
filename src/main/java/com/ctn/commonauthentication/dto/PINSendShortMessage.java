package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.exception.IllegalValueException;
import com.ctn.commonauthentication.service.MessageContents;
import com.ctn.commonauthentication.service.MessageDestination;
import com.ctn.commonauthentication.serviceImpl.CpassSmsService;
import software.amazon.awssdk.services.sns.model.PublishRequest;

public class PINSendShortMessage implements MessageContents {

    private CpassSmsService cpassSmsService;
    private Customer destination;
    private PIN pin;
    private String content = """
            認証番号「%s」をWEBサイトで入力してください。
            30分間有効です。
            """;

    public PINSendShortMessage(PIN pin) {
        this.pin = pin;
    }

    @Override
    public PublishRequest to_request() throws IllegalValueException {
        return PublishRequest
                .builder()
                .message(content.formatted(this.pin.number()))
                .phoneNumber(destination.destinationFormat())
                .build();
    }

    @Override
    public MessageContents to(MessageDestination d) throws IllegalValueException {
        if (d instanceof Customer) {
            this.destination = (Customer)d;
        } else {
            throw new IllegalValueException();
        }
        return this;
    }

}
