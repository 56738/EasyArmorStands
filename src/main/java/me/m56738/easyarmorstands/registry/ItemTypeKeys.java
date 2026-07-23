package me.m56738.easyarmorstands.registry;

import me.m56738.easyarmorstands.platform.Platform;
import net.kyori.adventure.key.Key;

public final class ItemTypeKeys {
    private ItemTypeKeys() {
    }

    public static final Key AIR = Key.key("air");
    public static final Key ARMOR_STAND = Key.key("armor_stand");
    public static final Key BEDROCK = Key.key("bedrock");
    public static final Key BLACK_STAINED_GLASS = Key.key("black_stained_glass");
    public static final Key BLUE_BANNER = Key.key("blue_banner");
    public static final Key BLUE_CONCRETE = Key.key("blue_concrete");
    public static final Key BONE_MEAL = Key.key("bone_meal");
    public static final Key BRUSH = Key.key("brush");
    public static final Key FEATHER = Key.key("feather");
    public static final Key GLASS_PANE = Key.key("glass_pane");
    public static final Key GLOWSTONE_DUST = Key.key("glowstone_dust");
    public static final Key GOLDEN_APPLE = Key.key("golden_apple");
    public static final Key GRAY_CONCRETE = Key.key("gray_concrete");
    public static final Key IRON_BARS = Key.key("iron_bars");
    public static final Key IRON_CHESTPLATE = Key.key("iron_chestplate");
    public static final Key IRON_HELMET = Key.key("iron_helmet");
    public static final Key IRON_SWORD = Key.key("iron_sword");
    public static final Key LEVER = Key.key("lever");
    public static final Key LIGHT_BLUE_STAINED_GLASS_PANE = Key.key("light_blue_stained_glass_pane");
    public static final Key LIGHT_GRAY_CONCRETE = Key.key("light_gray_concrete");
    public static final Key LIME_CONCRETE = Key.key("lime_concrete");
    public static final Key NAME_TAG = Key.key("name_tag");
    public static final Key NOTE_BLOCK = Key.key("note_block");
    public static final Key OAK_PRESSURE_PLATE = Key.key("oak_pressure_plate");
    public static final Key OAK_STAIRS = Key.key("oak_stairs");
    public static final Key PLAYER_HEAD = Key.key("player_head");
    public static final Key POTION = Key.key("potion");
    public static final Key REDSTONE_BLOCK = Key.key("redstone_block");
    public static final Key RED_CONCRETE = Key.key("red_concrete");
    public static final Key STICK = Key.key("stick");
    public static final Key STONE = Key.key("stone");
    public static final Key STONE_SLAB = Key.key("stone_slab");
    public static final Key SUNFLOWER = Key.key("sunflower");
    public static final Key TARGET = Key.key("target");
    public static final Key TNT = Key.key("tnt");
    public static final Key YELLOW_STAINED_GLASS_PANE = Key.key("yellow_stained_glass_pane");

    public static void validate(Platform platform) {
        RegistryUtil.validate(ItemTypeKeys.class, platform::getItemType);
    }
}
