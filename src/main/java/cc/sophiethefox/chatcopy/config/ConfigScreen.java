package cc.sophiethefox.chatcopy.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private ButtonWidget notificationsToggle;
    private ButtonWidget chatMessagesToggle;

    public ConfigScreen(Screen parent) {
        super(Text.literal("Chat Copy Config"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int startY = 80;
        int spacing = 50;

        // On-screen notifications toggle
        this.notificationsToggle = ButtonWidget.builder(getNotificationsText(), button -> toggleNotifications()).dimensions(centerX - 100, startY, 200, 20).build();
//        this.addDrawableChild(this.notificationsToggle);

        // Chat messages toggle
        this.chatMessagesToggle = ButtonWidget.builder(getChatMessagesText(), button -> toggleChatMessages()).dimensions(centerX - 100, startY + spacing, 200, 20).build();
//        this.addDrawableChild(this.chatMessagesToggle);

        // Done button
//        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> this.close()).dimensions(centerX - 50, this.height - 40, 100, 20).build());

        this.addDrawableChild(new TextWidget(Text.of("No config options yet. Please adjust keybind in controls menu under the Multiplayer category."), this.textRenderer));
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

    private Text getNotificationsText() {
        if (ModConfig.onScreenNotifications) {
            // .withColor introduced in later ver
            // formatted(...) supported as far back as 1.20 at least.
            return Text.literal("On-screen Notifications: ").append(Text.literal("ON").formatted(Formatting.GREEN));

        } else {
            return Text.literal("On-screen Notifications: ").append(Text.literal("OFF").formatted(Formatting.RED));
        }
    }

    private Text getChatMessagesText() {
        if (ModConfig.chatMessages) {
            return Text.literal("Chat Messages: ").append(Text.literal("ON").formatted(Formatting.GREEN));
        } else {
            return Text.literal("Chat Messages: ").append(Text.literal("OFF").formatted(Formatting.RED));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        //#if MC<=12003
        //$$ this.renderBackground(context);
        //#endif
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 40, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
