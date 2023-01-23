package gg.bundlegroup.easyarmorstands.common.platform;

import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

public interface EasEntity extends EasWrapper {
    void update();

    void teleport(Vector3dc position, float yaw, float pitch);

    void setPersistent(boolean persistent);

    boolean isGlowing();

    void setGlowing(boolean glowing);

    boolean isInvulnerable();

    void setInvulnerable(boolean invulnerable);

    Component getCustomName();

    void setCustomName(Component customName);

    boolean isCustomNameVisible();

    void setCustomNameVisible(boolean visible);

    Vector3dc getPosition();

    EasWorld getWorld();

    boolean isValid();

    void remove();
}
