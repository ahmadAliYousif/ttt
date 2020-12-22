package com.example.myapplication.khamast;


import java.util.ArrayList;

public class UserDetails {


    static String username = "";
    static String password = "";
    static String chatWith = "";
    static Orde reciver ;
    static ArrayList<Orde> AllUsers ;

    public static ArrayList<Orde> getAllUsers() {
        return AllUsers;
    }

    public static void setAllUsers(ArrayList<Orde> allUsers) {
        AllUsers = allUsers;
    }

    public static Orde getReciver() {
        return reciver;
    }

    public static void setReciver(Orde reciver) {
        UserDetails.reciver = reciver;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserDetails.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserDetails.password = password;
    }

    public static String getChatWith() {
        return chatWith;
    }

    public static void setChatWith(String chatWith) {
        UserDetails.chatWith = chatWith;
    }
}
