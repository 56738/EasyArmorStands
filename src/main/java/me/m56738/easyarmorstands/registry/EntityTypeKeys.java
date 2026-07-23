package me.m56738.easyarmorstands.registry;

import me.m56738.easyarmorstands.platform.Platform;
import net.kyori.adventure.key.Key;

public final class EntityTypeKeys {
    private EntityTypeKeys() {
    }

    public static final Key ARMOR_STAND = Key.key("armor_stand");
    public static final Key ITEM_DISPLAY = Key.key("item_display");
    public static final Key BLOCK_DISPLAY = Key.key("block_display");
    public static final Key TEXT_DISPLAY = Key.key("text_display");
    public static final Key INTERACTION = Key.key("interaction");
    public static final Key MANNEQUIN = Key.key("mannequin");

    public static void validate(Platform platform) {
        RegistryUtil.validate(EntityTypeKeys.class, platform::getEntityType);
    }
}
