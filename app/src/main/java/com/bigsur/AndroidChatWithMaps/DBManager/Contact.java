package com.bigsur.AndroidChatWithMaps.DBManager;


public class Contact {
    private int id;
    private String name;
    private String phone_number;

    public Contact() {}

    public Contact(String name) {
        this.name = name;
    }

    public Contact(String name, String phone_number) {
        this.name = name;
        this.phone_number = phone_number;
    }

    public Contact(int id, String inputData) {
        this.id = id;
        char lastSymbol = name.charAt(name.length());

        if (Character.isDigit(lastSymbol)) {
            this.phone_number = inputData;
        } else {
            this.name = inputData;
        }
    }

    public Contact (int id, String name, String phone_number) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
