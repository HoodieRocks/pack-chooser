package me.cobble.datapackchooser.mixin;

import com.mojang.datafixers.DataFixer;
import me.cobble.datapackchooser.utils.FileDownloader;
import me.cobble.datapackchooser.utils.PackManifests;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ApiServices;
import net.minecraft.world.level.storage.LevelStorage;
import okhttp3.OkHttpClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.Proxy;

@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin extends MinecraftServer {

    public IntegratedServerMixin(Thread serverThread, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, Proxy proxy, DataFixer dataFixer, ApiServices apiServices, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory) {
        super(serverThread, session, dataPackManager, saveLoader, proxy, dataFixer, apiServices, worldGenerationProgressListenerFactory);
    }

    @Inject(at = @At("RETURN"), method = "setupServer")
    private void addResources(CallbackInfoReturnable<Boolean> cir) {
        FileDownloader downloader = new FileDownloader(new OkHttpClient());
        downloader.downloadResourcepack(PackManifests.getByName("More TNT").get("rp_url").getAsString(), this.getSaveProperties().getLevelName());
    }
}
