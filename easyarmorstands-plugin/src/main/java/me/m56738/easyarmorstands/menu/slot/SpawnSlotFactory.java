package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpawnSlotFactory implements MenuSlotFactory {
    private final ElementType type;
    private final SimpleItemTemplate buttonTemplate;

    public SpawnSlotFactory(ElementType type, SimpleItemTemplate buttonTemplate) {
        this.type = type;
        this.buttonTemplate = buttonTemplate;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        if (type.canSpawn(PaperPlayer.fromNative(context.player()))) {
            return new SpawnSlot(type, buttonTemplate,
                    TagResolver.builder()
                            .resolver(context.resolver())
                            .tag("type", Tag.selfClosingInserting(type.getDisplayName()))
                            .build());
        } else {
            return null;
        }
    }
}
