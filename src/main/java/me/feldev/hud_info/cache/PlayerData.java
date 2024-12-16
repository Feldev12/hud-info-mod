package me.feldev.hud_info.cache;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerData implements Comparable<PlayerData> {

    public UUID uniqueId;
    public String displayName;
    public int points;

    public PlayerData(UUID uniqueId) {
        this(uniqueId, "", 0);
    }

    public PlayerData(UUID uniqueId, String displayName, int points) {
        this.uniqueId = uniqueId;
        this.displayName = displayName;
        this.points = points;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public int getPoints() {
        return points;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int compareTo(@NotNull PlayerData o) {
        return Integer.compare(o.points, points);
    }
}
