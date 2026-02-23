package com.ctn.commonauthentication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PatternKey implements Serializable {
    @Column(name = "shopid")
    private int shopId;
    @Column(name = "accident_history")
    private String accidentHistory;
    @Column(name = "runnable")
    private String runnable;
}
