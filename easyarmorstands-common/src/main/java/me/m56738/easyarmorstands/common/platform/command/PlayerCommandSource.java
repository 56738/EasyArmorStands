package me.m56738.easyarmorstands.common.platform.command;

import me.m56738.easyarmorstands.api.platform.entity.Player;

public interface PlayerCommandSource extends CommandSource {
    @Override
    Player source();
}
