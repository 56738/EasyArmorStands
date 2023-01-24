package gg.bundlegroup.easyarmorstands.bukkit.feature;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public interface ArmorStandLockAccessor {
    boolean isLocked(ArmorStand armorStand);

    void setLocked(ArmorStand armorStand, boolean locked);

    interface Provider extends FeatureProvider<ArmorStandLockAccessor> {
    }

    class Fallback implements Provider {
        private Accessor accessor;

        public Fallback() {
            try {
                accessor = new Accessor();
            } catch (Throwable ignored) {
            }
        }

        @Override
        public boolean isSupported() {
            return accessor != null;
        }

        @Override
        public Priority getPriority() {
            return Priority.FALLBACK;
        }

        @Override
        public ArmorStandLockAccessor create() {
            return accessor;
        }

        private static class Accessor implements ArmorStandLockAccessor {
            private final MethodHandle getHandle;
            private final Field lockedSlotsField;

            public Accessor() throws Throwable {
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
}
