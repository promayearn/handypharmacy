package cstu.handypharmacy.app;

import cstu.handypharmacy.webService.ServerName;

public class EndPoints {

    // localhost url
    // public static final String BASE_URL = "http://192.168.5.223/gcm_chat/v1";
    // public static final String BASE_URL = "http://handypharmacy.darczone.com/gcm_chat/v1";

    //public static final String BASE_URL = "http://192.168.235.1/gcm_chat/v1";
    public static final String BASE_URL = ServerName.BASE_SERVER + "gcm_chat/v1";
    public static final String LOGIN = BASE_URL + "/user/login";
    public static final String USER = BASE_URL + "/user/_ID_";
    public static final String CHAT_ROOMS = BASE_URL + "/chat_rooms";
    public static final String CHAT_THREAD = BASE_URL + "/chat_rooms/_ID_";
    public static final String CHAT_ROOM_MESSAGE = BASE_URL + "/chat_rooms/_ID_/message";
}
