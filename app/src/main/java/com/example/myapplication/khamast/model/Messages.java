package com.example.myapplication.khamast.model;

import com.example.myapplication.khamast.model.msg;

import java.util.ArrayList;

public class Messages {
    String senderKey ;
    String senderName ;
    String reciverKey ;
    String reciverNmae ;
    ArrayList<msg> msgs ;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReciverKey() {
        return reciverKey;
    }

    public void setReciverKey(String reciverKey) {
        this.reciverKey = reciverKey;
    }

    public String getReciverNmae() {
        return reciverNmae;
    }

    public void setReciverNmae(String reciverNmae) {
        this.reciverNmae = reciverNmae;
    }

    public ArrayList<msg> getMsgs() {
        return msgs;
    }

    public void setMsgs(ArrayList<msg> msgs) {
        this.msgs = msgs;
    }

    public String getSenderKey() {
        return senderKey;
    }

    public void setSenderKey(String senderKey) {
        this.senderKey = senderKey;
    }
}
