package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuBooleanInput;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuNotifier;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class BooleanTogglePropertyType extends BooleanPropertyType {
    public BooleanTogglePropertyType(@NotNull Key key) {
        super(key);
    }

    @Override
    public void addToMenu(@NotNull MenuBuilder builder, @NotNull Property<Boolean> property, @NotNull PropertyContainer properties, @NotNull MenuNotifier notifier) {
        MenuBooleanInput input = builder.addBooleanInput(getName(), property.getValue());
        notifier.addSubmitAction(() -> property.setValue(input.getValue()));
        notifier.addCommitAction(properties::commit);
    }
}
