package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot;

import org.bukkit.profile.PlayerTextures;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class TextureAdapter {
    private final MethodHandle getProperty;
    private final MethodHandle getValue;

    private static TextureAdapter instance;

    public TextureAdapter(Class<?> texturesClass) throws ReflectiveOperationException {
        Class<?> propertyClass = Class.forName("com.mojang.authlib.properties.Property");

        Method getPropertyMethod = texturesClass.getDeclaredMethod("getProperty");
        getPropertyMethod.setAccessible(true);
        this.getProperty = MethodHandles.lookup().unreflect(getPropertyMethod);

        Method getValueMethod = propertyClass.getDeclaredMethod("value");
        this.getValue = MethodHandles.lookup().unreflect(getValueMethod);
    }

    public static String getValue(PlayerTextures textures) throws Throwable {
        if (instance == null) {
            instance = new TextureAdapter(textures.getClass());
        }
        Object property = instance.getProperty.invoke(textures);
        Object value = instance.getValue.invoke(property);
        return (String) value;
    }
}
