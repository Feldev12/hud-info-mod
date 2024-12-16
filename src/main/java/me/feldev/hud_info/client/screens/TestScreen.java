package me.feldev.hud_info.client.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TestScreen extends Screen {
    protected TestScreen(Component p_96550_) {
        super(p_96550_);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int width = this.width / 2;
        int height = this.height / 2;
        graphics.drawString(this.minecraft.font, "Hello World", width, height, 0xFFFFFF);
        super.render(graphics, mouseX, mouseY, partialTick);
    }
}
