package cc.sophiethefox.chatcopy.mixin;

import cc.sophiethefox.chatcopy.ChatUtil;
import net.minecraft.client.MinecraftClient;
//#if MC>=12109
import net.minecraft.client.gui.Click;
//#endif
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
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
    @Inject(method = "handleClickEvent", at = @At(value = "HEAD"), cancellable = true)
    private void onHandleClickEvent(Style style, boolean insert, CallbackInfoReturnable<Boolean> cir) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        long handle = minecraftClient.getWindow().getHandle();
        if (GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_LEFT_ALT) == 0) {
            return;
        }
        cir.setReturnValue(false);
        cir.cancel();
    }

    @Inject(method = "mouseClicked", at = @At(value = "HEAD"))
    //#if MC>=12109
    private void onChatMessageClicked(Click click, boolean doubled, CallbackInfoReturnable<Boolean> cir) {
        if (click.button() == 0) {
            //#else
            //$$ private void onChatMessageClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
            //$$ if(button == 0) {
            //#endif
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            long handle = minecraftClient.getWindow().getHandle();
            if (GLFW.glfwGetKey(handle, GLFW.GLFW_KEY_LEFT_ALT) == 0) {
                return;
            }

            //#if MC>=12109
            double mouseY = click.y();
            double mouseX = click.x();
            //#endif
            ChatHud chatHud = minecraftClient.inGameHud.getChatHud();
            List<ChatHudLine.Visible> visibleMessages = ((ChatHudAccessor) chatHud).visibleMessages();
            List<ChatHudLine> messages = ((ChatHudAccessor) chatHud).getMessages();

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

            ChatHudLine message = messages.get(messageIndex);
            if (message == null) {
                return;
            }

            try {
                minecraftClient.keyboard.setClipboard(Formatting.strip(message.content().getString()));
                // volume & pitch are used as some version before 1.20.6 does not have a method overload which has defualt values
                minecraftClient.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            } catch (Exception e) {
                // clipboard can be null for some reason ?
            }
        }
    }
}
