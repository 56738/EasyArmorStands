package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogResponseView;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiFunction;

public interface ModdedDialogResponseView extends DialogResponseView, ModdedPlatformHolder {
    Tag getNative();

    static ModdedDialogResponseView fromNative(ModdedPlatform platform, Tag tag) {
        return new ModdedDialogResponseViewImpl(platform, tag);
    }

    static Tag toNative(DialogResponseView view) {
        return ((ModdedDialogResponseView) view).getNative();
    }

    default <T> @Nullable T getValue(String key, BiFunction<CompoundTag, String, Optional<T>> getter) {
        return getNative().asCompound().flatMap(t -> getter.apply(t, key)).orElse(null);
    }

    @Override
    default @Nullable Boolean getBoolean(String key) {
        return getValue(key, CompoundTag::getBoolean);
    }

    @Override
    default @Nullable String getText(String key) {
        return getValue(key, CompoundTag::getString);
    }
}
