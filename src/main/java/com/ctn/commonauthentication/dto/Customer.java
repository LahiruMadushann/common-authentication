package com.ctn.commonauthentication.dto;

import com.ctn.commonauthentication.exception.IllegalValueException;
import com.ctn.commonauthentication.service.MessageDestination;

public class Customer implements MessageDestination {
    private CTNPhoneNo phone;
    private String name;
    private CTNEmail email;
    private String post_number;
    private String prefecture;
    private String municipalities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost_number() {
        return post_number;
    }

    public void setPost_number(String post_number) {
        this.post_number = post_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public PIN getPin() {
        return pin;
    }

    public void setPin(PIN pin) {
        this.pin = pin;
    }

    private String address;
    private String address_detail;
    private PIN pin;

    private Customer(){}

    public Customer(CTNPhoneNo phone, String name, CTNEmail email, String post_number, String prefecture,
                    String municipalities, String address, String address_detail) {
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.post_number = post_number;
        this.prefecture = prefecture;
        this.municipalities = municipalities;
        this.address = address;
        this.address_detail = address_detail;
    }

    public Customer(String phone, String name, String email, String post_number, String prefecture,
                    String municipalities, String address, String address_detail) throws IllegalValueException {
        if (phone != null) {
            this.phone = new CTNPhoneNo(phone);
        }
        this.name = name;
        if (email != null) {
            this.email = new CTNEmail(email);
        }
        this.post_number = post_number;
        this.prefecture = prefecture;
        this.municipalities = municipalities;
        this.address = address;
        this.address_detail = address_detail;
    }

    public Customer(String phone, String name, String email, String post_number, String prefecture,
                    String municipalities, String address, String address_detail, PIN pin) throws IllegalValueException {
        if (phone != null) {
            this.phone = new CTNPhoneNo(phone);
        }
        this.name = name;
        if (email != null) {
            this.email = new CTNEmail(email);
        }
        this.post_number = post_number;
        this.prefecture = prefecture;
        this.municipalities = municipalities;
        this.address = address;
        this.address_detail = address_detail;
        this.pin = pin;
    }

    public Customer(String phoneNo) throws IllegalValueException {
        this(phoneNo, null,null,null,null,null,null,null, new PIN());
    }


    @Override
    public Type contactType() {
        return Type.SMS;
    }
    @Override
    public boolean unreachable() {
        return this.phone == null;
    }

    public String destinationFormat() {
        return this.phone.internationalFormat();
    }


    public void sendPIN() throws IllegalValueException {
        Message
                .body(this.pin)
                .to(this)
                .send();
    }

    public District area() {
        return new District(this.post_number, this.prefecture, this.municipalities, false);
    }

    public String toSendFormat () {
        return this.email.toString();

    }

    public CTNPhoneNo getPhone() {
        return phone;
    }

    public void setPhone(CTNPhoneNo phone) {
        this.phone = phone;
    }

    public CTNEmail getEmail() {
        return email;
    }

    public void setEmail(CTNEmail email) {
        this.email = email;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(String municipalities) {
        this.municipalities = municipalities;
    }
}
