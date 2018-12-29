package com.bigsur.AndroidChatWithMaps.UI;


import java.util.Date;

public interface DataWithIcon {

    int getId();

    String getName();

    String getSubname();

    String getAvatar();

    Date getExtraTitle();

    Boolean getExtraTitleIcon();

    void setName(String name);
}