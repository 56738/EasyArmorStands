package me.m56738.easyarmorstands.property.type;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.property.button.ProfilePropertyButton;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.object.ObjectContents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class ProfilePropertyType extends ConfigurablePropertyType<ResolvableProfile> {
    public ProfilePropertyType(@NotNull Key key) {
        super(key, ResolvableProfile.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull ResolvableProfile value) {
        return Component.object(ObjectContents.playerHead(value)).color(NamedTextColor.WHITE);
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Property<ResolvableProfile> property, @NotNull PropertyContainer container) {
        return new ProfilePropertyButton(property, container, buttonTemplate);
    }
}
