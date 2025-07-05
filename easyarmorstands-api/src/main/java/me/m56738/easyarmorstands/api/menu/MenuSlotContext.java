package me.m56738.easyarmorstands.api.menu;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.kyori.adventure.permission.PermissionChecker;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@ApiStatus.NonExtendable
public interface MenuSlotContext {
    @NotNull Player player();

    @Nullable Session session();

    @Nullable Element element();

    @Nullable PropertyContainer properties();

    @NotNull PropertyContainer properties(@NotNull Element element);

    @NotNull PermissionChecker permissions();

    @NotNull Locale locale();

    @NotNull TagResolver resolver();

    @Nullable ColorPickerContext colorPicker();
}
