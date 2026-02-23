package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.entity.AWS;
import com.ctn.commonauthentication.exception.IllegalValueException;
import software.amazon.awssdk.awscore.AwsRequest;

public interface MessageContents {

    public AwsRequest to_request() throws IllegalValueException;

    public MessageContents to(MessageDestination d) throws IllegalValueException;

    public default void send() throws IllegalValueException {
        AWS.messageRequest(this.to_request()).send();

    }
}
