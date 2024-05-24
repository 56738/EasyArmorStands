package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.api.util.ItemTemplate;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.item.ItemRenderer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Type;
import java.util.List;

public class ItemTemplateSerializer implements TypeSerializer<ItemTemplate> {
    private final @Nullable MethodHandle customModelDataSetter;

    public ItemTemplateSerializer() {
        customModelDataSetter = findCustomModelDataSetter();
    }

    @SuppressWarnings("JavaLangInvokeHandleSignature")
    private static MethodHandle findCustomModelDataSetter() {
        try {
            return MethodHandles.lookup().findVirtual(ItemMeta.class, "setCustomModelData",
                    MethodType.methodType(void.class, Integer.class));
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    @Override
    public ItemTemplate deserialize(Type type, ConfigurationNode node) throws SerializationException {
        ItemStack template = new ItemStack(
                node.node("type").get(Material.class, Material.AIR),
                node.node("amount").getInt(1),
                (short) node.node("data").getInt());
        ItemMeta meta = template.getItemMeta();
        if (meta != null) {
            if (customModelDataSetter != null) {
                try {
                    customModelDataSetter.invoke(meta, node.node("custom-model-data").get(Integer.class));
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
            template.setItemMeta(meta);
        }
        String name = node.node("name").getString();
        List<String> description = node.node("description").getList(String.class);
        return new SimpleItemTemplate(template, name, description, TagResolver.empty(), ItemRenderer.button());
    }

    @Override
    public void serialize(Type type, @Nullable ItemTemplate obj, ConfigurationNode node) throws SerializationException {
        throw new SerializationException(new UnsupportedOperationException());
    }
}
