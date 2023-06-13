package me.m56738.easyarmorstands.property;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated
public interface LegacyEntityPropertyType<E extends Entity, T> extends EntityPropertyType<T>, EntityPropertyAccessor<E, T> {
    @Override
    default Property<T> bind(Entity entity) {
        try {
            return new EntityPropertyBinding<>(this, getEntityType().cast(entity), this);
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    default Component getValueComponent(T value) {
        return getValueName(value);
    }

    TypeToken<T> getValueType();

    @NotNull String getName();

    @NotNull Class<E> getEntityType();

    ArgumentParser<EasCommandSender, T> getArgumentParser();

    default boolean hasDefaultValue() {
        return false;
    }

    default @Nullable T getDefaultValue(@NotNull CommandContext<EasCommandSender> ctx) {
        return null;
    }

    @NotNull Component getDisplayName();

    @NotNull Component getValueName(T value);

    default @NotNull String getValueClipboardContent(T value) {
        return PlainTextComponentSerializer.plainText().serialize(getValueName(value));
    }

    @Nullable String getPermission();

    default boolean isCreativeModeRequired() {
        return false;
    }

    default boolean isSupported(E entity) {
        return true;
    }

    default boolean performChange(ChangeContext context, E entity, T value) {
        return context.tryChange(new PropertyChange<>(bind(entity), value));
    }
}
