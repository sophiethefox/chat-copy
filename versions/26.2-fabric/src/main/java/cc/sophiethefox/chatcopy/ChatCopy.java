package cc.sophiethefox.chatcopy;

import cc.sophiethefox.chatcopy.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class ChatCopy implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConfig.copyToClipboardModifier = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.chat-copy.copymodifier", // The translation key for the key mapping.
                InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT,
                KeyMapping.Category.MULTIPLAYER
        ));
    }
}