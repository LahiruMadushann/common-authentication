package com.ctn.commonauthentication.service;

public interface MessageDestination {

    public enum Type {
        Email, SMS
    }

    public Type contactType();
    public boolean unreachable();
}
