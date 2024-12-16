package me.feldev.hud_info.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import me.feldev.hud_info.HudInfoMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.lwjgl.opengl.GL11;

public class PlayerInfoGuiOverlay implements IGuiOverlay {
    private int currentPlayers;
    private int maxPlayers;

    private int currentKills;

    private final ResourceLocation peopleTexture = new ResourceLocation(HudInfoMod.MOD_ID,"textures/gui/player_info_board/people.png");
    private final ResourceLocation swordTexture = new ResourceLocation(HudInfoMod.MOD_ID,"textures/gui/player_info_board/sword.png");
    private final int iconsTextureSize = 10;
    private int x = 100;
    private int y = 20;
    @Override
    public void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTicks, int width, int height) {
        //double scale = forgeGui.getMinecraft().getWindow().getGuiScale();
        //lowScreen - highScreen
        int overlayWidth = width <= 427 ? 8 : 20;
        int overlayHeight = height <= 240 ? 85 : 150;

        int backgroundX = overlayWidth + x;
        int backgroundY = overlayHeight + y;

        int backgroundLogoColor = forgeGui.getMinecraft().options.getBackgroundColor(0.3f);

        int iconsTextureHeight = overlayHeight + 5;
        int textHeight = iconsTextureHeight + 2;

        int peopleTextureWidth = overlayWidth + 5;
        int peopleTextWidth = peopleTextureWidth + 15;

        int swordTextureWidth = overlayWidth + 65;
        int swordTextWidth = swordTextureWidth + 15;


        guiGraphics.fill(overlayWidth, overlayHeight, backgroundX, backgroundY, backgroundLogoColor);
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, peopleTexture);
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        guiGraphics.blit(peopleTexture, peopleTextureWidth, iconsTextureHeight, 0, 0, iconsTextureSize, iconsTextureSize, iconsTextureSize ,iconsTextureSize);
        guiGraphics.drawString(forgeGui.getMinecraft().font, currentPlayers + "/" + maxPlayers, peopleTextWidth, textHeight, 0xFFFFFF);
        guiGraphics.blit(swordTexture, swordTextureWidth, iconsTextureHeight, 0, 0, iconsTextureSize, iconsTextureSize, iconsTextureSize ,iconsTextureSize);
        guiGraphics.drawString(forgeGui.getMinecraft().font, String.valueOf(currentKills), swordTextWidth, textHeight, 0xFFFFFF);
    }

    public void setData(int currentPlayers, int maxPlayers, int currentKills) {
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
        this.currentKills = currentKills;
    }
}
