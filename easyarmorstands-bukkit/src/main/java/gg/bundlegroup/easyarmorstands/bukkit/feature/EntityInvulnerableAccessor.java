package gg.bundlegroup.easyarmorstands.bukkit.feature;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface EntityInvulnerableAccessor {
    boolean isInvulnerable(Entity entity);

    void setInvulnerable(Entity entity, boolean invulnerable);

    interface Provider extends FeatureProvider<EntityInvulnerableAccessor> {
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
        public EntityInvulnerableAccessor create() {
            return accessor;
        }

        private static class Accessor implements EntityInvulnerableAccessor {
            private final MethodHandle getHandle;
            private final Field invulnerableField;

            public Accessor() throws Throwable {
                String serverPackage = Bukkit.getServer().getClass().getName();
                serverPackage = serverPackage.substring(0, serverPackage.lastIndexOf('.'));
                Class<?> entityClass = Class.forName(serverPackage + ".entity.CraftEntity");
                Method getHandleMethod = entityClass.getDeclaredMethod("getHandle");
                Class<?> entityHandleClass = getHandleMethod.getReturnType();
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                getHandle = lookup.unreflect(getHandleMethod);
                invulnerableField = entityHandleClass.getDeclaredField("invulnerable");
                invulnerableField.setAccessible(true);
            }

            @Override
            public boolean isInvulnerable(Entity entity) {
                try {
                    return invulnerableField.getBoolean(getHandle.invoke(entity));
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void setInvulnerable(Entity entity, boolean locked) {
                try {
                    invulnerableField.setBoolean(getHandle.invoke(entity), locked);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
