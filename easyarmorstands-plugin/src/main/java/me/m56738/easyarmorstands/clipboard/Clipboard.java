package me.m56738.easyarmorstands.clipboard;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import me.m56738.easyarmorstands.util.PropertyCopier;
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

    public void handleAutoApply(Element element, EasPlayer player) {
        if (this.properties.isEmpty()) {
            return;
        }

        PropertyCopier copier = new PropertyCopier();
        TrackedPropertyContainer properties = new TrackedPropertyContainer(element, player);
        copier.copyProperties(properties, this.properties);
        properties.commit(Message.component("easyarmorstands.history.clipboard-pasted-automatically"));

        if (copier.getSuccessCount() > 0) {
            player.sendMessage(Message.hint("easyarmorstands.hint.clipboard-auto-applied"));
            player.sendMessage(Message.hint("easyarmorstands.hint.clear-clipboard", Message.command("/eas clipboard clear")));
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
