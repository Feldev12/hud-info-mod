package me.feldev.hud_info.client.handlers.network;

import me.feldev.hud_info.HudInfoMod;
import me.feldev.hud_info.client.handlers.network.constants.MessageType;
import me.feldev.hud_info.client.handlers.network.messages.PlayerInfoMessage;
import me.feldev.hud_info.client.handlers.network.messages.SidebarMessage;
import me.feldev.hud_info.client.handlers.network.messages.interfaces.PluginMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.event.EventNetworkChannel;

import java.util.HashMap;
import java.util.Map;

public class MessagesHandler {
    private final Map<String, PluginMessage> messages;
    private EventNetworkChannel channel;
    private final ResourceLocation named = new ResourceLocation(HudInfoMod.MOD_ID, "messages");

    public MessagesHandler() {
        this.messages = new HashMap<>();
        loadMessages();
    }

    private void loadMessages() {
        messages.put(MessageType.SIDEBAR.getName(), new SidebarMessage());
        messages.put(MessageType.PLAYER_INFO.getName(), new PlayerInfoMessage());
    }

    public void register() {
        channel = NetworkRegistry.ChannelBuilder
                .named(named)
                .networkProtocolVersion(() -> "1")
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .eventNetworkChannel();
        channel.addListener(event -> {
            FriendlyByteBuf buf = event.getPayload();
            if(buf == null) return;
            int messageTypeId = buf.readByte();
            MessageType messageType = MessageType.fromId(messageTypeId);
            if(messageType == null) return;
            executeMessage(messageType.getName(), event);
        });
    }

    public void executeMessage(String key, NetworkEvent event) {
        PluginMessage message = messages.get(key);
        if(message == null) return;
        message.read(event);
    }
}
