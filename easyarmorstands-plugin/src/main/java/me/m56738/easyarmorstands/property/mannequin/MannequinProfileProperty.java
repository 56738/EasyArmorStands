package me.m56738.easyarmorstands.property.mannequin;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Mannequin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class MannequinProfileProperty implements Property<ResolvableProfile> {
    private final Mannequin entity;

    public MannequinProfileProperty(Mannequin entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<ResolvableProfile> getType() {
        return MannequinPropertyTypes.PROFILE;
    }

    @Override
    public @NotNull ResolvableProfile getValue() {
        return entity.getProfile();
    }

    @Override
    public boolean setValue(@NotNull ResolvableProfile value) {
        entity.setProfile(value);
        return true;
    }
}
