package gg.bundlegroup.easyarmorstands.common.platform.test;

import cloud.commandframework.CommandManager;
import gg.bundlegroup.easyarmorstands.common.inventory.SessionMenu;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.common.platform.EasFeature;
import gg.bundlegroup.easyarmorstands.common.platform.EasInventory;
import gg.bundlegroup.easyarmorstands.common.platform.EasInventoryListener;
import gg.bundlegroup.easyarmorstands.common.platform.EasItem;
import gg.bundlegroup.easyarmorstands.common.platform.EasListener;
import gg.bundlegroup.easyarmorstands.common.platform.EasMaterial;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.common.session.Session;
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
    public boolean canMoveSession(Session session, Vector3dc position) {
        return true;
    }

    @Override
    public void onSessionStarted(Session session) {
    }

    @Override
    public void onInventoryInitialize(SessionMenu inventory) {
    }
}