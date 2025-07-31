package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import org.jspecify.annotations.NullMarked;

import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
public final class InteractionPropertyTypes {
    public static final PropertyType<Boolean> RESPONSIVE = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "interaction/responsive"))
            .name(translatable("easyarmorstands.property.interaction.responsive.name"))
            .permission("easyarmorstands.property.interaction.responsive"));

    private InteractionPropertyTypes() {
    }
}
