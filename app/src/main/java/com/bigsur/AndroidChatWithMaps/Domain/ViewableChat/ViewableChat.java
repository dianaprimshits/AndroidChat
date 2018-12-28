package com.bigsur.AndroidChatWithMaps.Domain.ViewableChat;


import com.bigsur.AndroidChatWithMaps.DB.ChatRooms.ChatRooms;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.DB.Messages.Messages;

import java.util.Date;

public class ViewableChat implements DataWithIcon {
    ChatRooms chat;
    String subTitle;
    Date extraTitle;
    Boolean extraTitleIcon;

    protected ViewableChat(ChatRooms chat, Messages message) {
        this.chat = chat;

        if(message != null) {
            subTitle = message.getMessage();
            extraTitle = message.getDate();
            extraTitleIcon = false;
        }
    }

    public ViewableChat(String title) {
        chat = new ChatRooms(title);
    }

    protected ChatRooms getChat() {
        return chat;
    }

    @Override
    public int getId() {
        return chat.getId();
    }

    @Override
    public String getName() {
        return chat.getName();
    }

    @Override
    public String getSubname() {
        return subTitle == null ? "" : subTitle;
    }

    @Override
    public int getAvatar() {
        return chat.getAvatar();
    }

    @Override
    public Date getExtraTitle() {
        return extraTitle;
    }

    @Override
    public Boolean getExtraTitleIcon() {
        return extraTitleIcon;
    }

    @Override
    public void setName(String name) {
        chat.setChatRoomName(name);
    }


}
