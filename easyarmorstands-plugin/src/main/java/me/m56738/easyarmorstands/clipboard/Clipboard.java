package me.m56738.easyarmorstands.clipboard;

import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.message.Message;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Clipboard {
    private final Player player;
    private final PropertyMap properties = new PropertyMap();

    Clipboard(Player player) {
        this.player = player;
    }

    public PropertyMap getProperties() {
        return properties;
    }

    public <T> void handlePropertyShiftClick(Property<T> property, MenuClick click) {
        PropertyType<T> type = property.getType();
        if (type.canCopy(click.player())) {
            properties.put(type, property.getValue());
            click.sendMessage(Message.success("easyarmorstands.success.property-copied", type.getName()));
        }
    }

    void removeDisallowed() {
        List<PropertyType<?>> types = new ArrayList<>();
        properties.forEach(property -> types.add(property.getType()));

        for (PropertyType<?> type : types) {
            if (!type.canCopy(player)) {
                properties.remove(type);
            }
        }
    }
}
