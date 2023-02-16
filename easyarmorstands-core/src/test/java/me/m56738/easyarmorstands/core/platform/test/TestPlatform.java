package me.m56738.easyarmorstands.core.platform.test;

import cloud.commandframework.CommandManager;
import me.m56738.easyarmorstands.core.inventory.SessionMenu;
import me.m56738.easyarmorstands.core.platform.EasArmorEntity;
import me.m56738.easyarmorstands.core.platform.EasArmorStand;
import me.m56738.easyarmorstands.core.platform.EasCommandSender;
import me.m56738.easyarmorstands.core.platform.EasFeature;
import me.m56738.easyarmorstands.core.platform.EasInventory;
import me.m56738.easyarmorstands.core.platform.EasInventoryListener;
import me.m56738.easyarmorstands.core.platform.EasItem;
import me.m56738.easyarmorstands.core.platform.EasListener;
import me.m56738.easyarmorstands.core.platform.EasMaterial;
import me.m56738.easyarmorstands.core.platform.EasPlatform;
import me.m56738.easyarmorstands.core.platform.EasPlayer;
import me.m56738.easyarmorstands.core.platform.EasWorld;
import me.m56738.easyarmorstands.core.session.Session;
import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestPlatform implements EasPlatform {
    private final TestCommandManager commandManager = new TestCommandManager();
    private final Set<TestPlayer> players = new HashSet<>();

    public TestWorld createWorld() {
        return new TestWorld(this);
    }

    public TestItem createItem(boolean tool) {
        return new TestItem(this, tool);
    }

    @Override
    public CommandManager<EasCommandSender> commandManager() {
        return commandManager;
    }

    @Override
    public boolean hasFeature(EasFeature feature) {
        return false;
    }

    @Override
    public boolean hasSlot(EasArmorEntity.Slot slot) {
        return true;
    }

    @Override
    public Collection<TestPlayer> getPlayers() {
        return players;
    }

    @Override
    public EasInventory createInventory(Component title, int width, int height, EasInventoryListener listener) {
        return new TestInventory(this);
    }

    @Override
    public EasItem createItem(EasMaterial material, Component name, List<Component> lore) {
        return createItem(false);
    }

    @Override
    public void registerListener(EasListener listener) {
    }

    @Override
    public void registerTickTask(Runnable task) {
    }

    @Override
    public boolean canStartSession(EasPlayer player, EasArmorStand armorStand) {
        return true;
    }

    @Override
    public boolean canSpawnArmorStand(EasPlayer player, Vector3dc position) {
        return true;
    }

    @Override
    public boolean canMoveSession(Session session, EasWorld world, Vector3dc position) {
        return true;
    }

    @Override
    public void onSessionStarted(Session session) {
    }

    @Override
    public void onInventoryInitialize(SessionMenu inventory) {
    }
}
