// adapted from Skytils, https://github.com/Skytils/SkytilsMod/blob/2.x/mod/src/main/kotlin/gg/skytils/skytilsmod/features/impl/handlers/ChatTabs.kt#L278-L306
// used with permission from sychic <3

package cc.sophiethefox.chatcopy;

import cc.sophiethefox.chatcopy.mixin.ChatHudAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;

public class ChatUtil {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static double getChatScale() {
        return mc.options.getChatScale().getValue();
    }

    public static double toChatLineX(double x) {
        return x / getChatScale() - 4.0;
    }

    public static double toChatLineY(double y) {
        return (mc.getWindow().getScaledHeight() - y - 40) / (getChatScale() * 9.0 * (mc.options.getChatLineSpacing().getValue() + 1.0));
    }

    public static int getMessageLineIndex(ChatHud chatHud, double x, double y) {
        double width = ChatHud.getWidth(MinecraftClient.getInstance().options.getChatWidth().getValue());
        if (x < -4.0 || x > (width / getChatScale())) return -1;
        int totalLines = Math.min(chatHud.getVisibleLineCount(), ((ChatHudAccessor) chatHud).visibleMessages().size());
        int yInt = (int) y;
        if (yInt >= 1 && yInt <= totalLines) {
            int withScrollOffset = (int) Math.floor(y + ((ChatHudAccessor) chatHud).scrolledLines());
            if (withScrollOffset >= 0 && withScrollOffset < ((ChatHudAccessor) chatHud).visibleMessages().size()) {
                return withScrollOffset;
            }
        }
        return yInt;
    }
}
