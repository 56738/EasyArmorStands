package gg.bundlegroup.easyarmorstands.platform;

public interface EasListener {
    boolean onLeftClick(EasPlayer player);

    boolean onLeftClickArmorStand(EasPlayer player, EasArmorStand armorStand);

    boolean onRightClick(EasPlayer player);

    boolean onRightClickArmorStand(EasPlayer player, EasArmorStand armorStand);

    void onScroll(EasPlayer player, int from, int to);

    void onLogin(EasPlayer player);

    void onJoin(EasPlayer player);

    void onQuit(EasPlayer player);
}
