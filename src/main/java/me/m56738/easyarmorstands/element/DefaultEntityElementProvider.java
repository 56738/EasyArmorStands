package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DefaultEntityElementProvider<E extends Entity> extends SimpleEntityElementProvider<E> {
    private final EasyArmorStandsCommon eas;

    public DefaultEntityElementProvider(EasyArmorStandsCommon eas, DefaultEntityElementType<E> type) {
        super(eas, type);
        this.eas = eas;
    }

    @Override
    public Element getElement(@NotNull Entity entity) {
        if (entity instanceof Player && !entity.hasMetadata("NPC") && !eas.getConfiguration().editor.allowPlayers) {
            return null;
        }
        return super.getElement(entity);
    }
}
