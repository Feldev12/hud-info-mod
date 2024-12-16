package me.feldev.hud_info.client.handlers.network.packets;

import me.feldev.hud_info.HudInfoMod;
import me.feldev.hud_info.cache.PlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ExampleS2CPacket {
    private List<PlayerData> players;

    public ExampleS2CPacket() {

    }

    public ExampleS2CPacket(List<PlayerData> players) {
        this.players = players;
    }

    public ExampleS2CPacket(FriendlyByteBuf buf) {
        players = new ArrayList<>();
        int size = buf.readByte();
        if(size > 0) {
            for (int i = 0; i < size; i++) {
                players.add(new PlayerData(buf.readUUID(), buf.readUtf(), buf.readInt()));
            }
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeByte(players.size());
        for(PlayerData playerData : players) {
            buf.writeUUID(playerData.getUniqueId());
            buf.writeUtf(playerData.getDisplayName());
            buf.writeInt(playerData.getPoints());
        }
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if(player == null) {
                HudInfoMod.LOGGER.error("Player is null");
                return;
            }
            HudInfoMod.ClientModEvents.getTestGuiOverlay().setPlayers(players);
        });
    }
}
