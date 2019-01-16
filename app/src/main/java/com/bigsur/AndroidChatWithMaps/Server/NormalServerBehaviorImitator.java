package com.bigsur.AndroidChatWithMaps.Server;

import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.util.NoSuchElementException;

public class NormalServerBehaviorImitator {

    public static Contacts contactWithIdGenerating(Contacts contact) {
        ViewableContactManager manager = ViewableContactManager.getInstance();
        Integer maxId = null;
        try {
            maxId = manager.getAll().stream()
                    .mapToInt(DataWithIcon::getId)
                    .max().orElseThrow(NoSuchElementException::new);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        contact.set_id(maxId + 1);
        return contact;
    }
}
