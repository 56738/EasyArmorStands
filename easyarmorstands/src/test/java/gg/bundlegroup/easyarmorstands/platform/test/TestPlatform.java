package gg.bundlegroup.easyarmorstands.platform.test;

import cloud.commandframework.CommandManager;
import gg.bundlegroup.easyarmorstands.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.platform.EasListener;
import gg.bundlegroup.easyarmorstands.platform.EasPlatform;

import java.util.Collection;
import java.util.HashSet;
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
    public boolean canHideEntities() {
        return true;
    }

    @Override
    public boolean canSetEntityPersistence() {
        return true;
    }

    @Override
    public boolean canSetEntityGlowing() {
        return true;
    }

    @Override
    public boolean canSpawnParticles() {
        return false;
    }

    @Override
    public Collection<TestPlayer> getPlayers() {
        return players;
    }

    @Override
    public void registerListener(EasListener listener) {
    }

    @Override
    public void registerTickTask(Runnable task) {
    }
}
