package me.cobble.datapackchooser.mixin;

import me.cobble.datapackchooser.client.menus.MarketplaceMenuContainer;
import me.cobble.datapackchooser.client.menus.MarketplaceRoot;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.pack.PackListWidget;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(PackScreen.class)
public abstract class PackScreenMixin extends Screen {

    @Shadow
    @Final
    private File file;

    @Shadow @Final private Screen parent;

    protected PackScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "init")
    private void addButton(CallbackInfo ci) {
        if(!(this.parent instanceof OptionsScreen)) {
            this.addDrawableChild(new ButtonWidget(this.width / 2 - 154, this.height - 20, 308, 20, Text.of("Choose From Online"),
                    button -> MinecraftClient.getInstance().setScreen(new MarketplaceMenuContainer(MinecraftClient.getInstance().currentScreen, new MarketplaceRoot(this.file)))));
        }
    }
}
