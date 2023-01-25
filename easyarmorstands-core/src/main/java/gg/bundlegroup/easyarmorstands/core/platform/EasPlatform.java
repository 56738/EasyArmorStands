package gg.bundlegroup.easyarmorstands.core.platform;

import cloud.commandframework.CommandManager;
import gg.bundlegroup.easyarmorstands.core.inventory.SessionMenu;
import gg.bundlegroup.easyarmorstands.core.session.Session;
import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

import java.util.Collection;
import java.util.List;

public interface EasPlatform {
    CommandManager<EasCommandSender> commandManager();

    boolean hasFeature(EasFeature feature);

    boolean hasSlot(EasArmorEntity.Slot slot);

    Collection<? extends EasPlayer> getPlayers();

    EasInventory createInventory(Component title, int width, int height, EasInventoryListener listener);

    EasItem createItem(EasMaterial material, Component name, List<Component> lore);

    void registerListener(EasListener listener);

    void registerTickTask(Runnable task);

    boolean canStartSession(EasPlayer player, EasArmorStand armorStand);

    boolean canSpawnArmorStand(EasPlayer player, Vector3dc position);

    boolean canMoveSession(Session session, Vector3dc position);

    void onSessionStarted(Session session);

    void onInventoryInitialize(SessionMenu inventory);
}
