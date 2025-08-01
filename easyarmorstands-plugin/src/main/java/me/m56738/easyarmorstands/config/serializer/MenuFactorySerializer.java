package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.configurate.serialize.TypeSerializer;
import me.m56738.easyarmorstands.menu.factory.MenuFactoryBuilderImpl;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Type;

public class MenuFactorySerializer implements TypeSerializer<MenuFactory> {
    @Override
    public MenuFactory deserialize(Type type, ConfigurationNode node) throws SerializationException {
        MenuFactoryBuilderImpl factory = new MenuFactoryBuilderImpl();
        factory.setTitleTemplate(node.node("title").getString());
        factory.setHeight(node.node("height").getInt());
        factory.setBackground(node.node("background").get(MenuSlotFactory.class));
        for (ConfigurationNode slotNode : node.node("slots").childrenMap().values()) {
            if (!slotNode.node("enabled").getBoolean(true)) {
                continue;
            }
            MenuSlotFactory slotFactory = slotNode.get(MenuSlotFactory.class);
            int row = slotNode.node("row").getInt();
            int column = slotNode.node("column").getInt();
            MenuSlotFactory old = factory.setSlot(Menu.index(row, column), slotFactory);
            if (old != null) {
                throw new SerializationException(slotNode.node(), MenuSlotFactory.class, "Multiple entries for slot [" + row + ", " + column + "]");
            }
        }
        return factory.build();
    }

    @Override
    public void serialize(Type type, @Nullable MenuFactory obj, ConfigurationNode node) throws SerializationException {
        throw new SerializationException(new UnsupportedOperationException());
    }
}
