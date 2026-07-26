package me.m56738.easyarmorstands.platform.event.callback;

import me.m56738.easyarmorstands.platform.entity.Player;

public interface PlayerReplaceCallback {
    void onReplacePlayer(Player oldPlayer, Player newPlayer);
}
