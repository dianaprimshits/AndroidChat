package com.bigsur.AndroidChatWithMaps.Server.entities;


import java.util.ArrayList;

public class JsonChatRoom {
    private int id;
    private String name;
    private String avatar;
    private ArrayList<Integer> users;


    public JsonChatRoom(String name, String avatar, ArrayList<Integer> users) {
        this.name = name;
        this.avatar = avatar;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public ArrayList<Integer> getUsers() {
        return users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
