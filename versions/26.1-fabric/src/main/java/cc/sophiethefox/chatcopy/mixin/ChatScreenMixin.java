package cc.sophiethefox.chatcopy.mixin;

import cc.sophiethefox.chatcopy.ChatUtil;
import cc.sophiethefox.chatcopy.config.ModConfig;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {

    // TODO: config for modifier key, ability to log copies to chat, on screen notification for copies
    // DONE: disable click action of clicked message - 1.1.0

    // disable click event if holding modifier key

    @Inject(method = "handleComponentClicked", at = @At(value = "HEAD"), cancellable = true)
    private void onHandleClickEvent(Style style, boolean insert, CallbackInfoReturnable<Boolean> cir) {
        Minecraft minecraftClient = Minecraft.getInstance();
        long handle = minecraftClient.getWindow().handle();
        if (GLFW.glfwGetKey(handle, KeyMappingHelper.getBoundKeyOf(ModConfig.copyToClipboardModifier).getValue()) == 0) {
            return;
        }
        cir.setReturnValue(false);
        cir.cancel();
    }

    @Inject(method = "mouseClicked", at = @At(value = "HEAD"))
    private void onChatMessageClicked(MouseButtonEvent click, boolean doubled, CallbackInfoReturnable<Boolean> cir) {
        if (click.button() == 0) {
            Minecraft minecraftClient = Minecraft.getInstance();
            long handle = minecraftClient.getWindow().handle();
            if (GLFW.glfwGetKey(handle, KeyMappingHelper.getBoundKeyOf(ModConfig.copyToClipboardModifier).getValue()) == 0) {
                return;
            }

            double mouseY = click.y();
            double mouseX = click.x();

            ChatComponent chatHud = minecraftClient.gui.getChat();
            List<GuiMessage.Line> visibleMessages = ((ChatHudAccessor) chatHud).visibleMessages();
            List<GuiMessage> messages = ((ChatHudAccessor) chatHud).getMessages();

            int visibleMessageIndex = ChatUtil.getMessageLineIndex(chatHud, ChatUtil.toChatLineX(mouseX), ChatUtil.toChatLineY(mouseY));

            if (visibleMessageIndex < 0 || visibleMessageIndex >= visibleMessages.size()) {
                return;
            }

            int n = visibleMessages.size();
            int[] mapping = new int[n];
            int curMsg = -1;
            for (int i = 0; i < n; i++) {
                if (visibleMessages.get(i).endOfEntry()) {
                    curMsg++;
                }
                mapping[i] = curMsg;
            }

            int messageIndex = mapping[visibleMessageIndex];

            if (messageIndex < 0 || messageIndex >= messages.size()) return;

            GuiMessage message = messages.get(messageIndex);
            if (message == null) {
                return;
            }

            try {
                minecraftClient.keyboardHandler.setClipboard(ChatFormatting.stripFormatting(message.content().getString()));
                // volume & pitch are used as some version before 1.20.6 does not have a method overload which has defualt values
                minecraftClient.player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);
            } catch (Exception e) {
                // clipboard can be null for some reason ?
            }
        }
    }
}
