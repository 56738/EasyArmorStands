package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class TrainCartsModelListingSlotType implements MenuSlotType {
    public static final Key KEY = Key.key("easyarmorstands", "traincarts/model_browser");

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    @Override
    public @NotNull MenuSlotFactory load(@NotNull ConfigurationNode node) throws SerializationException {
        return new TrainCartsModelListingSlotFactory(
                node.node("item").get(SimpleItemTemplate.class));
    }
}
