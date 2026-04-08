package cc.sophiethefox.chatcopy.config;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private Button notificationsToggle;
    private Button chatMessagesToggle;

    public ConfigScreen(Screen parent) {
        super(Component.literal("Chat Copy Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int startY = 80;
        int spacing = 50;

        // On-screen notifications toggle
        this.notificationsToggle = Button.builder(getNotificationsText(), button -> toggleNotifications()).bounds(centerX - 100, startY, 200, 20).build();
//        this.addDrawableChild(this.notificationsToggle);

        // Chat messages toggle
        this.chatMessagesToggle = Button.builder(getChatMessagesText(), button -> toggleChatMessages()).bounds(centerX - 100, startY + spacing, 200, 20).build();
//        this.addDrawableChild(this.chatMessagesToggle);

        // Done button
//        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> this.close()).dimensions(centerX - 50, this.height - 40, 100, 20).build());

        this.addRenderableWidget(new StringWidget(Component.literal("No config options yet. Please adjust keybind in controls menu under the Multiplayer category."), this.font));
    }

    private void toggleNotifications() {
        ModConfig.onScreenNotifications = !ModConfig.onScreenNotifications;
        this.notificationsToggle.setMessage(getNotificationsText());
        ModConfig.save();
    }

    private void toggleChatMessages() {
        ModConfig.chatMessages = !ModConfig.chatMessages;
        this.chatMessagesToggle.setMessage(getChatMessagesText());
        ModConfig.save();
    }

    private Component getNotificationsText() {
        if (ModConfig.onScreenNotifications) {
            // .withColor introduced in later ver
            // formatted(...) supported as far back as 1.20 at least.
            return Component.literal("On-screen Notifications: ").append(Component.literal("ON").withStyle(ChatFormatting.GREEN));

        } else {
            return Component.literal("On-screen Notifications: ").append(Component.literal("OFF").withStyle(ChatFormatting.RED));
        }
    }

    private Component getChatMessagesText() {
        if (ModConfig.chatMessages) {
            return Component.literal("Chat Messages: ").append(Component.literal("ON").withStyle(ChatFormatting.GREEN));
        } else {
            return Component.literal("Chat Messages: ").append(Component.literal("OFF").withStyle(ChatFormatting.RED));
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        context.centeredText(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
        super.extractRenderState(context, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}
