package me.m56738.easyarmorstands.platform.neoforge;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permission;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.server.permission.PermissionAPI;

public class NeoForgePlatform extends ModdedPlatform {
    private final NeoForgePermissionManager permissionManager = new NeoForgePermissionManager();

    public NeoForgePlatform(ComponentLogger logger, IEventBus modBus) {
        super(logger);
        new NeoForgePlatformListener(this, modBus);
    }

    public NeoForgePermissionManager getPermissionManager() {
        return permissionManager;
    }

    @Override
    public String getName() {
        return "NeoForge";
    }

    @Override
    public String getVersion() {
        return FMLLoader.getCurrent().getVersionInfo().neoForgeVersion();
    }

    @Override
    public boolean hasPermission(ServerPlayer player, String permission) {
        return PermissionAPI.getPermission(player, permissionManager.get(permission));
    }

    @Override
    public boolean hasPermission(CommandSourceStack stack, String permission) {
        ServerPlayer player = stack.getPlayer();
        if (player != null) {
            return hasPermission(player, permission);
        } else {
            return stack.permissions().hasPermission(new Permission.HasCommandLevel(stack.getServer().operatorUserPermissions().level()));
        }
    }
}
