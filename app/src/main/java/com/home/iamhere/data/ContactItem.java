package com.home.iamhere.data;

public class ContactItem {
    public final String id;
    public final String name;
    public final String number;
    public final String photo;

    public ContactItem(String id, String name, String number, String photo) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return name;
    }
}
