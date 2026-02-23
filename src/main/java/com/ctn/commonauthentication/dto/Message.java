package com.ctn.commonauthentication.dto;

public class Message{

    private Message( ) {}

    public static PINSendShortMessage body(PIN pin) {
        return new PINSendShortMessage(pin);
    }

//    public static MatchedAssessmentEmail body(MatchedAssessmentEmailContents d) {
//        return new MatchedAssessmentEmail(d);
//    }

}
