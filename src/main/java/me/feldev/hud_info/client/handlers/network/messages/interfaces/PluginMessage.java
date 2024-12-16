package me.feldev.hud_info.client.handlers.network.messages.interfaces;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public interface PluginMessage {

    void read(NetworkEvent event);

    void toBytes(FriendlyByteBuf buf);
}
