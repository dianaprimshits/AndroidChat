package com.bigsur.AndroidChatWithMaps.Domain.ViewableChat;

import com.bigsur.AndroidChatWithMaps.DB.ChatRooms.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DB.ChatRooms.SQLiteChatRoomsManager;
import com.bigsur.AndroidChatWithMaps.DB.Messages.SQLiteMessagesManager;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;

import java.util.ArrayList;

public class ViewableChatManager extends DataWithIconManager {
    SQLiteChatRoomsManager chatManager;
    SQLiteMessagesManager messagesManager;

    static ViewableChatManager instance;

    private ViewableChatManager() {
    }

    private ViewableChatManager(SQLiteChatRoomsManager chatManager, SQLiteMessagesManager messagesManager) {
        this.chatManager = chatManager;
        this.messagesManager = messagesManager;
    }

    public static ViewableChatManager getInstance() {
        if (instance == null) {
            instance = new ViewableChatManager(SQLiteChatRoomsManager.getInstance(), SQLiteMessagesManager.getInstance());
        }
        return instance;
    }

    @Override
    public void create(DataWithIcon data, Runnable onSuccess, Runnable onFail) {
        chatManager.create(((ViewableChat) data).getChat());
    }

    @Override
    public void update(DataWithIcon data, Runnable onSuccess, Runnable onFail) {
        chatManager.update(((ViewableChat) data).getChat());
    }

    @Override
    public void delete(int id, Runnable onSuccess, Runnable onFail) {
        chatManager.delete(id);
    }

    @Override
    public DataWithIcon getById(int id) {
        return new ViewableChat((ChatRooms) chatManager.getById(id), messagesManager.getLastMessage(id));
    }

    @Override
    public ArrayList<DataWithIcon> getAll() {
        ArrayList<DataWithIcon> chats = chatManager.getAll();
        ArrayList<DataWithIcon> viewableChats = new ArrayList<>();

        for (DataWithIcon chat : chats) {
            int id = chat.getId();
            viewableChats.add(new ViewableChat((ChatRooms) chat, messagesManager.getLastMessage(id)));
        }

        return viewableChats;
    }

    @Override
    public void addDataChangeListener(Object listener, Runnable callBack) {
     //   chatManager.addDataChangeListener(listener, callBack);
    }

    @Override
    public void  removeDataChangeListener(Object listener) {
      //  chatManager.removeDataChangeListener(listener);
    }
}
