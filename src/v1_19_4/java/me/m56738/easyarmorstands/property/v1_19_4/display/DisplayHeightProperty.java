package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.property.ChangeContext;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyChange;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.List;

public class DisplayHeightProperty extends DisplaySizeProperty {
    private final DisplayAddon addon;

    public DisplayHeightProperty(DisplayAddon addon) {
        this.addon = addon;
    }

    @Override
    public Float getValue(Display entity) {
        return entity.getDisplayHeight();
    }

    @Override
    public void setValue(Display entity, Float value) {
        entity.setDisplayHeight(value);
    }

    @Override
    public @NotNull String getName() {
        return "height";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("height");
    }

    @Override
    public boolean performChange(ChangeContext context, Display entity, Float value) {
        Property<Vector3fc> displayTranslationProperty = addon.getDisplayTranslationProperty().bind(entity);
        Property<Location> entityLocationProperty = EasyArmorStands.getInstance().getEntityLocationProperty().bind(entity);

        float oldValue = entity.getDisplayHeight();
        float delta = (value - oldValue) / 2;

        // Attempt to keep the center in the same place
        List<PropertyChange<?>> changes = new ArrayList<>(2);

        // Move down using location
        Location location = entityLocationProperty.getValue();
        Location newLocation = location.clone();
        newLocation.setY(location.getY() - delta);
        changes.add(new PropertyChange<>(entityLocationProperty, newLocation));

        // Move up using offset
        Vector3f translation = new Vector3f(displayTranslationProperty.getValue());
        translation.y += delta;
        changes.add(new PropertyChange<>(displayTranslationProperty, translation));

        // Ignore the return value, failure is allowed
        context.tryChange(changes);

        return super.performChange(context, entity, value);
    }
}
