package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Player;

public class SessionSnapper implements Snapper {
    public static final double DEFAULT_OFFSET_INCREMENT = 1.0 / 32;
    public static final double DEFAULT_POSITION_INCREMENT = 1.0 / 32;
    public static final double DEFAULT_ANGLE_INCREMENT = 2 * Math.PI / 256;

    private final Player player;
    private double offsetIncrement = DEFAULT_OFFSET_INCREMENT;
    private double positionIncrement = DEFAULT_POSITION_INCREMENT;
    private double angleIncrement = DEFAULT_ANGLE_INCREMENT;

    public SessionSnapper(Player player) {
        this.player = player;
    }

    private double snap(double value, double increment) {
        if (player.isSneaking()) {
            return value;
        }
        return Util.snap(value, increment);
    }

    @Override
    public double snapOffset(double offset) {
        return snap(offset, offsetIncrement);
    }

    @Override
    public double snapPosition(double position) {
        return snap(position, positionIncrement);
    }

    @Override
    public double snapAngle(double angle) {
        return snap(angle, angleIncrement);
    }

    public double getOffsetIncrement() {
        return offsetIncrement;
    }

    public void setOffsetIncrement(double offsetIncrement) {
        this.offsetIncrement = offsetIncrement;
    }

    public double getPositionIncrement() {
        return positionIncrement;
    }

    public void setPositionIncrement(double positionIncrement) {
        this.positionIncrement = positionIncrement;
    }

    public double getAngleIncrement() {
        return angleIncrement;
    }

    public void setAngleIncrement(double angleIncrement) {
        this.angleIncrement = angleIncrement;
    }
}
