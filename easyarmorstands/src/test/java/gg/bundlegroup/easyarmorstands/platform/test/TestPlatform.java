package gg.bundlegroup.easyarmorstands.platform.test;

import gg.bundlegroup.easyarmorstands.platform.EasListener;
import gg.bundlegroup.easyarmorstands.platform.EasPlatform;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TestPlatform implements EasPlatform {
    private final Set<TestPlayer> players = new HashSet<>();

    public TestWorld createWorld() {
        return new TestWorld(this);
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
