package me.m56738.easyarmorstands.fancyholograms.property.display;

import de.oliver.fancyholograms.api.data.DisplayHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.util.JOMLMapper;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3fc;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class DisplayHologramScaleProperty implements Property<Vector3fc> {
    private final Hologram hologram;
    private final JOMLMapper mapper;
    private final MethodHandle getScale;
    private final MethodHandle setScale;

    public DisplayHologramScaleProperty(Hologram hologram, DisplayHologramData data, JOMLMapper mapper) {
        this.hologram = hologram;
        this.mapper = mapper;
        try {
            Class<?> vectorClass = mapper.getNativeVectorClass();
            this.getScale = MethodHandles.lookup().findVirtual(DisplayHologramData.class, "getScale", MethodType.methodType(vectorClass)).bindTo(data);
            this.setScale = MethodHandles.lookup().findVirtual(DisplayHologramData.class, "setScale", MethodType.methodType(DisplayHologramData.class, vectorClass)).bindTo(data);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull PropertyType<Vector3fc> getType() {
        return DisplayPropertyTypes.SCALE;
    }

    @Override
    public @NotNull Vector3fc getValue() {
        try {
            return mapper.convertFromNativeVector(getScale.invoke());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean setValue(@NotNull Vector3fc value) {
        try {
            setScale.invoke(mapper.convertToNative(value));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
