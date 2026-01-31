package cc.sophiethefox.chatcopy.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(method="handleTextClick", at = @At(value = "HEAD"), cancellable = true)
    protected void onHandleTextClick(Style style, CallbackInfoReturnable<Boolean> cir) {

    }
}
