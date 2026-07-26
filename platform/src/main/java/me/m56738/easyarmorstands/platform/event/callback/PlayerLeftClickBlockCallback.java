package me.m56738.easyarmorstands.platform.event.callback;

import me.m56738.easyarmorstands.platform.block.Block;
import me.m56738.easyarmorstands.platform.entity.Player;

public interface PlayerLeftClickBlockCallback {
    boolean onPlayerLeftClickBlock(Player player, Block block);
}
