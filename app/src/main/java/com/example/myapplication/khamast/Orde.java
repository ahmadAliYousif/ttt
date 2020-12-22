package com.example.myapplication.khamast;

import com.example.myapplication.khamast.model.Messages;
import com.example.myapplication.khamast.model.msg;

import java.util.ArrayList;

public class Orde {
    ArrayList<Messages> myMessages = new ArrayList<>(); ;
    String key;
    String name;
    String email;
    String passwords;
    public Orde() {
    }

    public Orde(ArrayList<Messages> myMessages, String key, String name, String email, String passwords) {
        this.myMessages = myMessages;
        this.key = key;
        this.name = name;
        this.email = email;
        this.passwords = passwords;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public ArrayList<Messages> getMyMessages() {
        return myMessages;
    }

    public void setMyMessages(ArrayList<Messages> myMessages) {
        this.myMessages = myMessages;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
