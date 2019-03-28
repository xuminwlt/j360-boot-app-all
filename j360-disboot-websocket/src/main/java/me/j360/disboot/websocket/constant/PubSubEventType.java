package me.j360.disboot.websocket.constant;

public enum PubSubEventType {

    HORN,
    ROOM_TEXT_MESSAGE,
    ROOM_GIFT,
    GAME_START,
    GAME_END,
    GAME_SUBJECT_NEXT,
    AUDIO_READY,
    USER_READY,
    FORCE_OFFLINE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
