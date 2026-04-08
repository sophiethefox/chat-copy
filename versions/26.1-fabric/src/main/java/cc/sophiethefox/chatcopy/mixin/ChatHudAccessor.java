package cc.sophiethefox.chatcopy.mixin;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ChatComponent.class)
public interface ChatHudAccessor {
    @Accessor("trimmedMessages")
    List<GuiMessage.Line> visibleMessages();

    @Accessor("allMessages")
    List<GuiMessage> getMessages();

    @Accessor("chatScrollbarPos")
    int scrolledLines();
}
