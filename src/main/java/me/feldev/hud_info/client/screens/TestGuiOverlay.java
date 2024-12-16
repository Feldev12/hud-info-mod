package me.feldev.hud_info.client.screens;

import me.feldev.hud_info.HudInfoMod;
import me.feldev.hud_info.cache.PlayerData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.List;

public class TestGuiOverlay implements IGuiOverlay {
    private List<PlayerData> players;
    ResourceLocation texture0 = new ResourceLocation(HudInfoMod.MOD_ID, "textures/gui/sideboard/0.png");
    ResourceLocation textureLogo = new ResourceLocation(HudInfoMod.MOD_ID, "textures/gui/sideboard/bannersv.png");

    private final int textureWidth = 150;
    private final int textureHeight = 20;

    private final int textureLogoWidth = 100;
    private final int textureLogoHeight = 25;


    @Override
    public void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTick, int width, int height) {
        int middleWidth = width - 155;
        int size = 10;
        int middleHeight = height / 2 + size / 3;

        int logoHeight = middleHeight - 140;
        int logoWidth = middleWidth + 25;

        int rankTextWidth = middleWidth + 6;
        int rankTextHeight = middleHeight + 6;

        int headPlayerWidth = rankTextWidth + 16;
        int playerTextWidth = headPlayerWidth + 12;

        int numberObjectiveWidth = width - 10;
        //CraftingEventForge.LOGGER.info("Rendering: width({}) height({})", width, height);
        int backgroundLogoColor = forgeGui.getMinecraft().options.getBackgroundColor(0.3f);
        int backgroundSlotColor = forgeGui.getMinecraft().options.getBackgroundColor(0.4f);
        guiGraphics.fill(middleWidth, logoHeight, middleWidth + 150, logoHeight + 25, backgroundLogoColor);
        guiGraphics.blit(textureLogo, logoWidth, logoHeight, 0, 0, textureLogoWidth, textureLogoHeight, textureLogoWidth, textureLogoHeight);
        int index = 0;
        if(players == null || players.isEmpty()) return;
        for(PlayerData playerData : players) {
            PlayerInfo playerInfo = forgeGui.getMinecraft().player.connection.getPlayerInfo(playerData.getUniqueId());
            if(playerInfo == null) {
                HudInfoMod.LOGGER.error("Player info ({}) is null", playerData.getUniqueId());
                continue;
            }
            int newLogoHeight = logoHeight + 25;
            int newMiddleHeight = newLogoHeight + index * 20;
            int newRankTextHeight = newMiddleHeight + 6;
            String pointsText = String.valueOf(playerData.getPoints());
            guiGraphics.drawString(forgeGui.getMinecraft().font, (index+1)+"°", rankTextWidth, newRankTextHeight, 0xFFFFFF);
            guiGraphics.fill(middleWidth, newMiddleHeight, middleWidth + textureWidth, newMiddleHeight + textureHeight, backgroundSlotColor);
            //guiGraphics.blit(texture0, middleWidth, newMiddleHeight, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
            PlayerFaceRenderer.draw(guiGraphics, playerInfo.getSkinLocation(), headPlayerWidth, newRankTextHeight, 8);

            guiGraphics.drawString(forgeGui.getMinecraft().font, playerData.getDisplayName(), playerTextWidth, newRankTextHeight, 0xFFFFFF);

            guiGraphics.drawString(forgeGui.getMinecraft().font, pointsText, numberObjectiveWidth - forgeGui.getFont().width(pointsText), newRankTextHeight, 0xFFFFFF);
            index++;
        }
        /*
        for(int i = 0; i < 10; i++) {
            int newLogoHeight = logoHeight + 25;
            int newMiddleHeight = newLogoHeight + i * 20;
            int newRankTextHeight = newMiddleHeight + 6;
            guiGraphics.drawString(forgeGui.getMinecraft().font, (i+1)+"°", rankTextWidth, newRankTextHeight, 0xFFFFFF);
            guiGraphics.fill(middleWidth, newMiddleHeight, middleWidth + textureWidth, newMiddleHeight + textureHeight, backgroundSlotColor);
            //guiGraphics.blit(texture0, middleWidth, newMiddleHeight, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
            PlayerFaceRenderer.draw(guiGraphics, forgeGui.getMinecraft().player.getSkinTextureLocation(), headPlayerWidth, newRankTextHeight, 8);

            guiGraphics.drawString(forgeGui.getMinecraft().font, forgeGui.getMinecraft().player.getName(), playerTextWidth, newRankTextHeight, 0xFFFFFF);

            guiGraphics.drawString(forgeGui.getMinecraft().font, "1", numberObjectiveWidth, newRankTextHeight, 0xFFFFFF);
        }
        */
        //ResourceLocation, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight
    }

    public void setPlayers(List<PlayerData> players) {
        this.players = players;
    }
}
