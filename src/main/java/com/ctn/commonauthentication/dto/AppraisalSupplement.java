package com.ctn.commonauthentication.dto;

public class AppraisalSupplement {
    private String note;
    private String message_for_matching_shop;
    private String ip;
    private String param;
    private String requestYMD;

    public AppraisalSupplement() { }

    public AppraisalSupplement(String note, String message_for_matching_shop, String ip, String param, String requestYMD) {
        this.note = note;
        this.message_for_matching_shop = message_for_matching_shop;
        this.ip = ip;
        this.param = param;
        if (requestYMD == null) {
            this.requestYMD = CTNDateTime.now().expression();
        }
        this.requestYMD = requestYMD;
    }


}
