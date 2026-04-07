package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.formatter.BooleanFormatter;
import org.jspecify.annotations.NullMarked;

import static me.m56738.easyarmorstands.api.EasyArmorStands.key;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
public final class InteractionPropertyTypes {
    public static final PropertyType<Boolean> RESPONSIVE = PropertyType.builder(key("interaction/responsive"), Boolean.class)
            .name(translatable("easyarmorstands.property.interaction.responsive.name"))
            .formatter(BooleanFormatter.toggle())
            .permission("easyarmorstands.property.interaction.responsive")
            .build();

    private InteractionPropertyTypes() {
    }
}
