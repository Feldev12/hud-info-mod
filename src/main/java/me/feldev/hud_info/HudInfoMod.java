package me.feldev.hud_info;

import com.mojang.logging.LogUtils;
import me.feldev.hud_info.client.handlers.network.MessagesHandler;
import me.feldev.hud_info.client.screens.PlayerInfoGuiOverlay;
import me.feldev.hud_info.client.screens.TestGuiOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HudInfoMod.MOD_ID)
public class HudInfoMod {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "hud_info";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    private final MessagesHandler messagesHandler;

    public HudInfoMod() {
        messagesHandler = new MessagesHandler();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        FMLJavaModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
//
//        if (Config.logDirtBlock)
//            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
//
//        LOGGER.info("{}{}", Config.magicNumberIntroduction, Config.magicNumber);
//
//        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
        messagesHandler.register();
    }

    @SubscribeEvent
    public void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        LOGGER.info("HELLO FROM PLAYER {} JOIN", player.getName());
        /*
        List<PlayerData> players = new ArrayList<>();
        players.add(new PlayerData(player.getUUID(), "Dev", 10));
        players.add(new PlayerData(player.getUUID(), "Abc", 5));
        players.add(new PlayerData(player.getUUID(), "§cSiiii", 2));
        Collections.sort(players);
        packetHandler.sendToAll(new ExampleS2CPacket(players));
        */
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        private static final TestGuiOverlay testGuiOverlay = new TestGuiOverlay();
        private static final PlayerInfoGuiOverlay playerInfoGuiOverlay = new PlayerInfoGuiOverlay();

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
//            for(int i = 0; i < Minecraft.getInstance().options.keyMappings.length; i++) {
//                KeyMapping keymapping = Minecraft.getInstance().options.keyMappings[i];
//                if(keymapping.getCategory().equalsIgnoreCase("Xaero's Minimap")) {
//                    LOGGER.info("KEYMAPPING >> {}", keymapping.getName());
//                    Minecraft.getInstance().options.keyMappings[i].setKeyConflictContext(CustomKeyConflictContext.NEVER);
//                }
//            }
        }

        @SubscribeEvent
        public static void registerGui(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll(MOD_ID + "_overlay_sidebard", testGuiOverlay);
            event.registerAboveAll(MOD_ID + "_overlay_playerinfo", playerInfoGuiOverlay);
        }

        public static TestGuiOverlay getTestGuiOverlay() {
            return testGuiOverlay;
        }

        public static PlayerInfoGuiOverlay getPlayerInfoGuiOverlay() {
            return playerInfoGuiOverlay;
        }
    }
}
