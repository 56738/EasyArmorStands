package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import me.m56738.easyarmorstands.capability.textdisplay.TextDisplayCapability;
import me.m56738.easyarmorstands.property.ComponentEntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextDisplayTextProperty extends ComponentEntityProperty<TextDisplay> {
    private final TextDisplayCapability textDisplayCapability;

    public TextDisplayTextProperty(TextDisplayCapability textDisplayCapability) {
        this.textDisplayCapability = textDisplayCapability;
    }

    @Override
    public Component getValue(TextDisplay entity) {
        return textDisplayCapability.getText(entity);
    }

    @Override
    public void setValue(TextDisplay entity, Component value) {
        textDisplayCapability.setText(entity, value);
    }

    @Override
    public @NotNull String getName() {
        return "text";
    }

    @Override
    public @NotNull Class<TextDisplay> getEntityType() {
        return TextDisplay.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("text");
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.text";
    }
}
