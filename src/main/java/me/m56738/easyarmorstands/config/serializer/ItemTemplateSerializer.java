package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.item.ItemRenderer;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.registry.ItemTypeKeys;
import me.m56738.easyarmorstands.util.ItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Supplier;

public class ItemTemplateSerializer implements TypeSerializer<ItemTemplate> {
    private final Platform platform;

    public ItemTemplateSerializer(Platform platform) {
        this.platform = platform;
    }

    @Override
    public ItemTemplate deserialize(Type type, ConfigurationNode node) throws SerializationException {
        ItemType itemType = node.node("type").get(ItemType.class, (Supplier<ItemType>) () -> platform.getItemType(ItemTypeKeys.AIR));
        ItemStack template = itemType.createItemStack(node.node("amount").getInt(1))
                .withHideTooltip(node.node("hide-tooltip").getBoolean())
                .withCustomModelData(node.node("custom-model-data").get(Integer.class));
        String name = node.node("name").getString();
        List<String> description = node.node("description").getList(String.class);
        return new SimpleItemTemplate(template, name, description, TagResolver.empty(), ItemRenderer.button());
    }

    @Override
    public void serialize(Type type, @Nullable ItemTemplate obj, ConfigurationNode node) throws SerializationException {
        throw new SerializationException(new UnsupportedOperationException());
    }
}
