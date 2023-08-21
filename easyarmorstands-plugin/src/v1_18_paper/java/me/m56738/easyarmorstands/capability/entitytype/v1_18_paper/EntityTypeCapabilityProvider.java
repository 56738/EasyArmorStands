package me.m56738.easyarmorstands.capability.entitytype.v1_18_paper;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class EntityTypeCapabilityProvider implements CapabilityProvider<EntityTypeCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class<?> type = Class.forName(String.join(".", "net", "kyori", "adventure", "translation", "Translatable"));
            MethodHandles.lookup().findVirtual(type, "translationKey", MethodType.methodType(String.class));
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    public EntityTypeCapability create(Plugin plugin) {
        return new EntityTypeCapabilityImpl();
    }

    private static class EntityTypeCapabilityImpl implements EntityTypeCapability {
        private final MethodHandle getTranslationKey;

        public EntityTypeCapabilityImpl() {
            try {
                Class<?> type = Class.forName(String.join(".", "net", "kyori", "adventure", "translation", "Translatable"));
                getTranslationKey = MethodHandles.lookup().findVirtual(type, "translationKey", MethodType.methodType(String.class));
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Component getName(EntityType type) {
            try {
                return Component.translatable((String) getTranslationKey.invoke(type));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
