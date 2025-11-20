package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.profile.Profile;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.NamedTextColor;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.format.Style;
import me.m56738.easyarmorstands.property.button.ProfilePropertyButton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProfilePropertyType extends ConfigurablePropertyType<Profile> {
    public ProfilePropertyType(@NotNull Key key) {
        super(key, Profile.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Profile value) {
        return value.asComponent().style(Style.style(NamedTextColor.WHITE));
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Property<Profile> property, @NotNull PropertyContainer container) {
        return new ProfilePropertyButton(property, container, buttonTemplate);
    }
}
