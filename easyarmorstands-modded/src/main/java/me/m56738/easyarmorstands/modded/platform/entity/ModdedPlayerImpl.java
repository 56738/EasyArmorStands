package me.m56738.easyarmorstands.modded.platform.entity;

import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.platform.world.World;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedPlayer;
import me.m56738.easyarmorstands.modded.api.platform.inventory.ModdedItem;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedWorld;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import org.joml.Vector3d;

public abstract class ModdedPlayerImpl extends ModdedCommandSenderImpl implements ModdedPlayer, ForwardingAudience.Single {
    private final ServerPlayer player;

    public ModdedPlayerImpl(ServerPlayer player) {
        super((Audience) player);
        this.player = player;
    }

    @Override
    public ServerPlayer getNative() {
        return player;
    }

    @Override
    public boolean isSneaking() {
        return player.isShiftKeyDown();
    }

    @Override
    public boolean isFlying() {
        return player.isFallFlying(); // TODO probably wrong
    }

    @Override
    public boolean isCreativeMode() {
        return player.gameMode() == GameType.CREATIVE;
    }

    @Override
    public Location getEyeLocation() {
        World world = ModdedWorld.fromNative(player.level());
        return Location.of(world, new Vector3d(player.getX(), player.getEyeY(), player.getZ()), player.getYRot(), player.getXRot());
    }

    @Override
    public void giveItem(Item item) {
        player.addItem(ModdedItem.toNative(item));
    }

    @Override
    public void openMenu(Menu menu) {
        // TODO
    }
}
