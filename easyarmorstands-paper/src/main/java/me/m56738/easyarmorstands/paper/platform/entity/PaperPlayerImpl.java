package me.m56738.easyarmorstands.paper.platform.entity;

import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import org.bukkit.entity.Player;

public class PaperPlayerImpl extends PaperCommandSenderImpl implements PaperPlayer {
    private final Player player;

    public PaperPlayerImpl(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public Player getNative() {
        return player;
    }

    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }

    @Override
    public boolean isValid() {
        return player.isValid();
    }
}
