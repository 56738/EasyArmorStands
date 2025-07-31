package me.m56738.easyarmorstands.common.clipboard;

import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.util.PropertyCopier;

import java.util.ArrayList;
import java.util.List;

public class Clipboard {
    private final EasyArmorStandsCommon eas;
    private final Player player;
    private final PropertyMap properties = new PropertyMap();

    Clipboard(EasyArmorStandsCommon eas, Player player) {
        this.eas = eas;
        this.player = player;
    }

    public PropertyMap getProperties() {
        return properties;
    }

    public void handleAutoApply(Element element, Player player) {
        if (this.properties.isEmpty()) {
            return;
        }

        PropertyCopier copier = new PropertyCopier();
        try (ManagedChangeContext context = eas.changeContext().create(player)) {
            copier.copyProperties(context.getProperties(element), properties);
            context.commit(Message.component("easyarmorstands.history.clipboard-pasted-automatically"));
        }

        if (copier.getSuccessCount() > 0) {
            player.sendMessage(Message.hint("easyarmorstands.hint.clipboard-auto-applied"));
            player.sendMessage(Message.hint("easyarmorstands.hint.clear-clipboard", Message.command("/eas clipboard clear")));
        }
    }

    public <T> void handlePropertyShiftClick(Property<T> property, Player player) {
        PropertyType<T> type = property.getType();
        if (player.hasPermission(Permissions.CLIPBOARD) && type.canCopy(player)) {
            properties.put(type, property.getValue());
            player.sendMessage(Message.success("easyarmorstands.success.property-copied", type.name()));
        }
    }

    public void removeDisallowed() {
        List<PropertyType<?>> types = new ArrayList<>();
        properties.forEach(property -> types.add(property.getType()));

        for (PropertyType<?> type : types) {
            if (!type.canCopy(player)) {
                properties.remove(type);
            }
        }
    }
}
