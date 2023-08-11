package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.editor.EntityObject;
import me.m56738.easyarmorstands.editor.EntityObjectProvider;
import me.m56738.easyarmorstands.event.EntityObjectInitializeEvent;
import me.m56738.easyarmorstands.property.entity.DefaultEntityProperties;
import org.bukkit.Bukkit;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;

public class DisplayObjectProvider<T extends Display> implements EntityObjectProvider {
    private final Class<T> type;
    private final DisplayRootNodeFactory<T> factory;

    public DisplayObjectProvider(Class<T> type, DisplayRootNodeFactory<T> factory) {
        this.type = type;
        this.factory = factory;
    }

    @Override
    public EntityObject createObject(Entity entity) {
        if (type.isInstance(entity)) {
            DisplayObject<T> displayObject = new DisplayObject<>(type.cast(entity), factory);
            DefaultEntityProperties.registerProperties(displayObject);
            Bukkit.getPluginManager().callEvent(new EntityObjectInitializeEvent(displayObject));
            return displayObject;
        }
        return null;
    }
}
