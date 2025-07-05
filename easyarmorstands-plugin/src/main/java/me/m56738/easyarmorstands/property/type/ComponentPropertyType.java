package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuNotifier;
import me.m56738.easyarmorstands.api.menu.MenuTextInput;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class ComponentPropertyType extends ConfigurablePropertyType<Component> {
    public ComponentPropertyType(@NotNull Key key, String command) {
        super(key, Component.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Component value) {
        return value;
    }

    @Override
    public void addToMenu(@NotNull MenuBuilder builder, @NotNull Property<Component> property, @NotNull PropertyContainer properties, @NotNull MenuNotifier notifier) {
        MenuTextInput input = builder.addTextInput(getName(), miniMessage().serialize(property.getValue()));
        notifier.addSubmitAction(() -> property.setValue(miniMessage().deserialize(input.getValue())));
        notifier.addCommitAction(properties::commit);
    }
}
