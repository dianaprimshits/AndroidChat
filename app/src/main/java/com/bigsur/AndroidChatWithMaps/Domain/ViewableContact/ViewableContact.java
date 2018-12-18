package com.bigsur.AndroidChatWithMaps.Domain.ViewableContact;

import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.util.Date;

public class ViewableContact implements DataWithIcon {
    Contacts contact;


    public ViewableContact(Contacts contact) {
        this.contact = contact;
    }

    public ViewableContact(String name, String phone) {
        contact = new Contacts(name, phone);
    }


    public Contacts getContact() {
        return contact;
    }

    @Override
    public int getId() {
        return contact.getId();
    }

    @Override
    public String getName() {
        return contact.getName();
    }

    @Override
    public String getSubname() {
        return contact.getSubname();
    }

    @Override
    public int getAvatar() {
        return contact.getAvatar();
    }

    @Override
    public Date getExtraTitle() {
        return null;
    }

    @Override
    public Boolean getExtraTitleIcon() {
        return null;
    }
}
