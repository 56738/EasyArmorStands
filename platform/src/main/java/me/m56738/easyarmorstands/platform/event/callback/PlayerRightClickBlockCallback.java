package me.m56738.easyarmorstands.platform.event.callback;

import me.m56738.easyarmorstands.platform.block.Block;
import me.m56738.easyarmorstands.platform.entity.Player;

public interface PlayerRightClickBlockCallback {
    boolean onPlayerRightClickBlock(Player player, Block block);
}
