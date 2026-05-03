package me.m56738.easyarmorstands.api.property.type;

import com.google.common.reflect.TypeToken;
import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.formatter.BooleanFormatter;
import me.m56738.easyarmorstands.api.formatter.OptionalFormatter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.object.ObjectContents;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.MainHand;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;
import java.util.Optional;

import static me.m56738.easyarmorstands.api.EasyArmorStands.key;
import static net.kyori.adventure.text.Component.translatable;

@SuppressWarnings("PatternValidation")
@NullMarked
public final class MannequinPropertyTypes {
    public static final PropertyType<MainHand> MAIN_HAND = PropertyType.builder(key("mannequin/main_hand"), MainHand.class)
            .name(translatable("easyarmorstands.property.mannequin.main-hand.name"))
            .formatter(value -> switch (value) {
                case LEFT -> translatable("easyarmorstands.property.common.hand.left");
                case RIGHT -> translatable("easyarmorstands.property.common.hand.right");
            })
            .permission("easyarmorstands.property.mannequin.mainhand")
            .build();
    @SuppressWarnings("UnstableApiUsage")
    public static final PropertyType<ResolvableProfile> PROFILE = PropertyType.builder(key("mannequin/profile"), ResolvableProfile.class)
            .name(translatable("easyarmorstands.property.mannequin.profile.name"))
            .formatter(value -> Component.object(ObjectContents.playerHead(value)))
            .permission("easyarmorstands.property.mannequin.profile")
            .build();
    public static final PropertyType<Boolean> IMMOVABLE = PropertyType.builder(key("mannequin/immovable"), Boolean.class)
            .name(translatable("easyarmorstands.property.mannequin.immovable.name"))
            .formatter(BooleanFormatter.translatable(
                    "easyarmorstands.property.mannequin.immovable.enabled",
                    "easyarmorstands.property.mannequin.immovable.disabled"))
            .permission("easyarmorstands.property.mannequin.immovable")
            .build();
    public static final PropertyType<Optional<Component>> DESCRIPTION = PropertyType.<Optional<Component>>builder(key("mannequin/description"))
            .name(translatable("easyarmorstands.property.mannequin.description.name"))
            .formatter(new OptionalFormatter<>(value -> value))
            .permission("easyarmorstands.property.mannequin.description")
            .build();
    public static final PropertyType<Pose> POSE = PropertyType.builder(key("mannequin/pose"), Pose.class)
            .name(translatable("easyarmorstands.property.mannequin.pose.name"))
            .formatter(value -> translatable("easyarmorstands.property.mannequin.pose." + value.name().toLowerCase(Locale.ROOT).replace("_", "-")))
            .permission("easyarmorstands.property.mannequin.pose")
            .build();
    public static final KeyedPropertyType<SkinPart, Boolean> SKIN_PART_VISIBLE = new EnumKeyedPropertyType<>(SkinPart.class,
            part -> PropertyType.builder(key("mannequin/part/" + part.name().toLowerCase(Locale.ROOT) + "/visible"), Boolean.class)
                    .name(translatable("easyarmorstands.property.mannequin.part.visible.name",
                            translatable("easyarmorstands.mannequin.part." + part.name().toLowerCase(Locale.ROOT).replace("_", "-"))))
                    .formatter(BooleanFormatter.visibility())
                    .permission(switch (part) {
                        case CAPE -> "easyarmorstands.property.mannequin.part.visible.cape";
                        case JACKET -> "easyarmorstands.property.mannequin.part.visible.jacket";
                        case LEFT_SLEEVE -> "easyarmorstands.property.mannequin.part.visible.sleeve.left";
                        case RIGHT_SLEEVE -> "easyarmorstands.property.mannequin.part.visible.sleeve.right";
                        case LEFT_PANTS -> "easyarmorstands.property.mannequin.part.visible.pants.left";
                        case RIGHT_PANTS -> "easyarmorstands.property.mannequin.part.visible.pants.right";
                        case HAT -> "easyarmorstands.property.mannequin.part.visible.hat";
                    })
                    .build());

    private MannequinPropertyTypes() {
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, TypeToken<T> type) {
        return EasyArmorStands.get().propertyTypeRegistry().get(Key.key("easyarmorstands", name), type);
    }

    private static <T> PropertyType<T> get(@KeyPattern.Value String name, Class<T> type) {
        return get(name, TypeToken.of(type));
    }
}
