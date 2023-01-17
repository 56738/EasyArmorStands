package gg.bundlegroup.easyarmorstands.platform;

import java.util.Collection;

public interface EasPlatform {
    boolean canHideEntities();

    boolean canSetEntityPersistence();

    boolean canSetEntityGlowing();

    Collection<? extends EasPlayer> getPlayers();

    void registerListener(EasListener listener);

    void registerTickTask(Runnable task);
}
