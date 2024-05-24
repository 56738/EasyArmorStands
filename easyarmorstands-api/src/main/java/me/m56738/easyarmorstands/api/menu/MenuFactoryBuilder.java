package me.m56738.easyarmorstands.api.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MenuFactoryBuilder {
    @Nullable
    MenuSlotFactory setSlot(int slot, @Nullable MenuSlotFactory factory);

    void setTitleTemplate(@NotNull String titleTemplate);

    void setHeight(int height);

    void setBackground(@Nullable MenuSlotFactory background);

    @NotNull
    MenuFactory build();
}
