package gg.bundlegroup.easyarmorstands.bukkit.paper;

import com.google.gson.JsonElement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

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

    public static NativeComponentMapper getInstance() {
        return instance;
    }

    public Class<?> getComponentClass() {
        return componentClass;
    }

    public Object convertToNative(Component component) {
        if (component == null) {
            return null;
        }
        try {
            return deserialize.invoke(serializer.serializeToTree(component));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Component convertFromNative(Object component) {
        if (component == null) {
            return null;
        }
        try {
            return serializer.deserializeFromTree((JsonElement) serialize.invoke(component));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
