package me.feldev.hud_info.client.handlers.network.messages;

import me.feldev.hud_info.HudInfoMod;
import me.feldev.hud_info.cache.PlayerData;
import me.feldev.hud_info.client.handlers.network.messages.interfaces.PluginMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class SidebarMessage implements PluginMessage {
    private List<PlayerData> players;

    public void read(NetworkEvent event) {
        players = new ArrayList<>();
        FriendlyByteBuf buf = event.getPayload();
        byte size = buf.readByte();
        //CraftingEvent.LOGGER.info("Reading size {}", size);
        if(size > 0) {
            for (int i = 0; i < size; i++) {
                UUID uuid = buf.readUUID();
                //CraftingEvent.LOGGER.info("Reading uuid {}", uuid);
                String displayName = buf.readUtf();
                //CraftingEvent.LOGGER.info("Reading displayName {}", displayName);
                int points = buf.readInt();
                //CraftingEvent.LOGGER.info("Reading points {}", points);
                players.add(new PlayerData(uuid, displayName, points));
            }
        }
        handle(event.getSource());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeByte(players.size());
        for(PlayerData playerData : players) {
            buf.writeUtf(playerData.getUniqueId().toString());
            buf.writeUtf(playerData.getDisplayName());
            buf.writeInt(playerData.getPoints());
        }
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            LocalPlayer player = Minecraft.getInstance().player;
            if(player == null) {
                //CraftingEvent.LOGGER.error("Player is null");
                return;
            }
            HudInfoMod.ClientModEvents.getTestGuiOverlay().setPlayers(players);
        });
    }
}
