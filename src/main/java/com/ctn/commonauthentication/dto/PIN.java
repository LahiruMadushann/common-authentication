package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.exception.IllegalValueException;

import java.time.LocalDateTime;
import java.util.Random;

public class PIN {
    private CTNPhoneNo dest;
    private String number;
    private LocalDateTime expiresAt;

    public PIN() {
        this.number = String.format("%04d", new Random().nextInt(9999));
        this.expiresAt = LocalDateTime.now().plusMinutes(30);
    }

    public PIN(String pinStr) throws IllegalValueException {
        if (!pinStr.matches("^\\d{4}")) throw new IllegalValueException();
        this.number = pinStr;
    }

    public boolean correct(String otherPIN) throws IllegalValueException {
        PIN other = new PIN(otherPIN);
        return this.valid(other);
    }

    public String number() {
        return this.number;
    }

    public boolean valid(PIN customersPIN) {
        return this.number.equals(customersPIN.number) && LocalDateTime.now().isBefore(customersPIN.expiresAt);
    }

    public boolean notValid(PIN input) {
        return !this.valid(input);
    }
}
