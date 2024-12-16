package me.feldev.hud_info.client.handlers.network.constants;

public enum MessageType {
    SIDEBAR(0),
    PLAYER_INFO(1);

    final int id;
    MessageType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public static MessageType fromId(int id) {
        for(MessageType type : values()) {
            if(type.id == id) return type;
        }
        return null;
    }
}
