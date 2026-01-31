package cc.sophiethefox.chatcopy;

import cc.sophiethefox.chatcopy.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ChatCopy implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConfig.copyToClipboardModifier = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.chat-copy.copymodifier", // The translation key for the key mapping.
                InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT,
                //#if MC>=12109
                KeyBinding.Category.MULTIPLAYER
                //#else
                //$$ KeyBinding.MULTIPLAYER_CATEGORY
                //#endif
        ));
    }
}