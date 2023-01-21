package gg.bundlegroup.easyarmorstands.common.platform;

import cloud.commandframework.CommandManager;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.text.Component;

import java.util.Collection;

public interface EasPlatform {
    CommandManager<EasCommandSender> commandManager();

    boolean hasFeature(EasFeature feature);

    boolean hasSlot(EasArmorEntity.Slot slot);

    Collection<? extends EasPlayer> getPlayers();

    EasInventory createInventory(Component title, int width, int height, EasInventoryListener listener);

    EasItem createPlaceholderItem();

    void registerListener(EasListener listener);

    void registerTickTask(Runnable task);

    boolean canStartSession(EasPlayer player, EasArmorStand armorStand);

    void onSessionStarted(Session session);
}
