package me.m56738.easyarmorstands.capability.lock.v1_8;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.Plugin;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class LockCapabilityProvider implements CapabilityProvider<LockCapability> {
    private LockCapabilityImpl instance;

    public LockCapabilityProvider() {
        try {
            instance = new LockCapabilityImpl();
        } catch (Throwable ignored) {
        }
    }

    @Override
    public boolean isSupported() {
        return instance != null;
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    public LockCapability create(Plugin plugin) {
        return instance;
    }

    private static class LockCapabilityImpl implements LockCapability {
        private final MethodHandle getHandle;
        private final Field lockedSlotsField;

        public LockCapabilityImpl() throws Throwable {
            String serverPackage = Bukkit.getServer().getClass().getName();
            serverPackage = serverPackage.substring(0, serverPackage.lastIndexOf('.'));
            Class<?> armorStandClass = Class.forName(serverPackage + ".entity.CraftArmorStand");
            Method getHandleMethod = armorStandClass.getDeclaredMethod("getHandle");
            Class<?> armorStandHandleClass = getHandleMethod.getReturnType();
            Field found = null;
            for (Field field : armorStandHandleClass.getDeclaredFields()) {
                if (field.getType() != Integer.TYPE) {
                    continue;
                }
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
                    continue;
                }
                if (found != null) {
                    throw new NoSuchFieldException("Multiple matching fields found");
                }
                found = field;
            }
            if (found == null) {
                throw new NoSuchFieldException("No matching field found");
            }
            found.setAccessible(true);
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            getHandle = lookup.unreflect(getHandleMethod);
            lockedSlotsField = found;
        }

        @Override
        public boolean isLocked(ArmorStand armorStand) {
            try {
                int slots = lockedSlotsField.getInt(getHandle.invoke(armorStand));
                return slots != 0;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void setLocked(ArmorStand armorStand, boolean locked) {
            try {
                lockedSlotsField.setInt(getHandle.invoke(armorStand), locked ? 0x3F3F3F : 0);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
