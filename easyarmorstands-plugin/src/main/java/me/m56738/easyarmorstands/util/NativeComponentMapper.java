package me.m56738.easyarmorstands.util;

import com.google.gson.JsonElement;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class NativeComponentMapper {
    private static final NativeComponentMapper instance;

    static {
        NativeComponentMapper inst = null;
        try {
            inst = initialize();
        } catch (Throwable ignored) {
        }
        instance = inst;
    }

    private final Class<?> componentClass;
    private final MethodHandle serialize;
    private final MethodHandle deserialize;
    private final GsonComponentSerializer serializer = GsonComponentSerializer.gson();

    private NativeComponentMapper(Class<?> componentClass, MethodHandle serialize, MethodHandle deserialize) {
        this.componentClass = componentClass;
        this.serialize = serialize;
        this.deserialize = deserialize;
    }

    private static NativeComponentMapper initialize() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String adventure = String.join(".", "net", "kyori", "adventure");
        Class<?> nativeComponentClass = Class.forName(adventure + ".text.Component");
        Class<?> nativeSerializerClass = Class.forName(adventure + ".text.serializer.gson.GsonComponentSerializer");
        Object nativeSerializer = nativeSerializerClass.getDeclaredMethod("gson").invoke(null);
        MethodHandle serialize = lookup.findVirtual(nativeSerializerClass, "serializeToTree",
                MethodType.methodType(JsonElement.class, nativeComponentClass)).bindTo(nativeSerializer);
        MethodHandle deserialize = lookup.findVirtual(nativeSerializerClass, "deserializeFromTree",
                MethodType.methodType(nativeComponentClass, JsonElement.class)).bindTo(nativeSerializer);
        return new NativeComponentMapper(nativeComponentClass, serialize, deserialize);
    }

    public static @Nullable NativeComponentMapper getInstance() {
        return instance;
    }

    public Class<?> getComponentClass() {
        return componentClass;
    }

    public @Nullable Object convertToNative(@Nullable Component component) {
        if (component == null) {
            return null;
        }
        if (componentClass.isAssignableFrom(component.getClass())) {
            return component;
        }
        try {
            return deserialize.invoke(serializer.serializeToTree(component));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public @Nullable Component convertFromNative(@Nullable Object component) {
        if (component == null) {
            return null;
        }
        if (component instanceof Component) {
            return (Component) component;
        }
        try {
            return serializer.deserializeFromTree((JsonElement) serialize.invoke(component));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
