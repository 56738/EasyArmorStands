package gg.bundlegroup.easyarmorstands.platform;

import cloud.commandframework.CommandManager;

import java.util.Collection;

public interface EasPlatform {
    CommandManager<EasCommandSender> commandManager();

    boolean canHideEntities();

    boolean canSetEntityPersistence();

    boolean canSetEntityGlowing();

    Collection<? extends EasPlayer> getPlayers();

    void registerListener(EasListener listener);

    void registerTickTask(Runnable task);
}
