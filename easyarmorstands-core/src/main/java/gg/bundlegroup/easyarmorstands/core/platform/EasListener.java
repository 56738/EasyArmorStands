package gg.bundlegroup.easyarmorstands.core.platform;

public interface EasListener {
    boolean onLeftClick(EasPlayer player, EasItem item);

    boolean onLeftClickArmorStand(EasPlayer player, EasArmorStand armorStand, EasItem item, boolean cancelled);

    boolean onRightClick(EasPlayer player, EasItem item);

    boolean onRightClickArmorStand(EasPlayer player, EasArmorStand armorStand, EasItem item, boolean cancelled);

    boolean onDrop(EasPlayer player, EasItem item);

    void onLogin(EasPlayer player);

    void onJoin(EasPlayer player);

    void onQuit(EasPlayer player);
}
