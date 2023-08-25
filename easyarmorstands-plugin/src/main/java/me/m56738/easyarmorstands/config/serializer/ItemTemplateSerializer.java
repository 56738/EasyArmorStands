package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.item.ItemRenderer;
import me.m56738.easyarmorstands.item.ItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.List;

public class ItemTemplateSerializer implements TypeSerializer<ItemTemplate> {
    @Override
    public ItemTemplate deserialize(Type type, ConfigurationNode node) throws SerializationException {
        ItemStack template = new ItemStack(
                node.node("type").get(Material.class, Material.AIR),
                node.node("amount").getInt(1));
        String name = node.node("name").getString();
        List<String> description = node.node("description").getList(String.class);
        return new ItemTemplate(template, name, description, TagResolver.empty(), ItemRenderer.button());
    }

    @Override
    public void serialize(Type type, @Nullable ItemTemplate obj, ConfigurationNode node) throws SerializationException {
        throw new SerializationException(new UnsupportedOperationException());
    }
}
