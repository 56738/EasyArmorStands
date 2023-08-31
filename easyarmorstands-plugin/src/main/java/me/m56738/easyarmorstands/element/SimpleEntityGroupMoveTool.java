package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.group.GroupMoveTool;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class SimpleEntityGroupMoveTool extends SimpleEntityGroupTool implements GroupMoveTool {
    public SimpleEntityGroupMoveTool(PropertyContainer properties) {
        super(properties);
    }

    @Override
    public void setOffset(@NotNull Vector3dc offset) {
        Location location = getOriginalLocation();
        location.add(offset.x(), offset.y(), offset.z());
        setLocation(location);
    }
}
