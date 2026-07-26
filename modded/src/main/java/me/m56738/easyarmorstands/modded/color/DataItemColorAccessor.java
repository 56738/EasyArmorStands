package me.m56738.easyarmorstands.modded.color;

import me.m56738.easyarmorstands.platform.color.RGBColor;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class DataItemColorAccessor<T> implements ItemColorAccessor {
    private final DataComponentType<T> type;
    private final T defaultValue;
    private final Predicate<Holder<Item>> predicate;
    private final IntFunction<T> provider;
    private final ToIntFunction<T> extractor;

    public DataItemColorAccessor(DataComponentType<T> type, T defaultValue, Predicate<Holder<Item>> predicate, IntFunction<T> provider, ToIntFunction<T> extractor) {
        this.type = type;
        this.defaultValue = defaultValue;
        this.predicate = predicate;
        this.provider = provider;
        this.extractor = extractor;
    }

    @Override
    public boolean isSupported(ItemStack item) {
        return item.has(type) || item.is(predicate);
    }

    @Override
    public RGBColor getColor(ItemStack item) {
        return RGBColor.of(extractor.applyAsInt(item.getOrDefault(type, defaultValue)));
    }

    @Override
    public void setColor(ItemStack item, RGBColor color) {
        item.set(type, provider.apply(color.value()));
    }
}
