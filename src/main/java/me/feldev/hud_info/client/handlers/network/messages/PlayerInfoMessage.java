package me.feldev.hud_info.client.handlers.network.messages;

import me.feldev.hud_info.HudInfoMod;
import me.feldev.hud_info.client.handlers.network.messages.interfaces.PluginMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerInfoMessage implements PluginMessage {
    private int currentPlayers;
    private int maxPlayers;

    private int currentKills;

    public void read(NetworkEvent event) {
        FriendlyByteBuf buf = event.getPayload();
        currentPlayers = buf.readShort();
        maxPlayers = buf.readShort();
        currentKills = buf.readByte();
        handle(event.getSource());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeShort(currentPlayers);
        buf.writeShort(maxPlayers);
        buf.writeByte(currentKills);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if(player == null) {
                HudInfoMod.LOGGER.error("Player is null");
                return;
            }
            HudInfoMod.ClientModEvents.getPlayerInfoGuiOverlay().setData(currentPlayers, maxPlayers, currentKills);
        });
    }
}
