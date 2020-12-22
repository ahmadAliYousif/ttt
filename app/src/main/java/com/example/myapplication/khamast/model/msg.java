package com.example.myapplication.khamast.model;

import java.util.Date;

public class msg {
    String message ;
    String senderKey ;
    String reciverKey ;
    Date sendDate ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderKey() {
        return senderKey;
    }

    public void setSenderKey(String senderKey) {
        this.senderKey = senderKey;
    }

    public String getReciverKey() {
        return reciverKey;
    }

    public void setReciverKey(String reciverKey) {
        this.reciverKey = reciverKey;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
