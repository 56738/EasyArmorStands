package gg.bundlegroup.easyarmorstands.bukkit.paper;

import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityNameAccessor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class EntityNameAccessorImpl implements EntityNameAccessor {
    private final NativeComponentMapper mapper;
    private final MethodHandle get;
    private final MethodHandle set;

    public EntityNameAccessorImpl(NativeComponentMapper mapper, MethodHandle get, MethodHandle set) {
        this.mapper = mapper;
        this.get = get;
        this.set = set;
    }

    @Override
    public Component getName(Entity entity) {
        try {
            return mapper.convertFromNative(get.invoke(entity));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setName(Entity entity, Component name) {
        try {
            set.invoke(entity, mapper.convertToNative(name));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static class Provider implements EntityNameAccessor.Provider {
        @Override
        public boolean isSupported() {
            return NativeComponentMapper.getInstance() != null;
        }

        @SuppressWarnings("JavaLangInvokeHandleSignature")
        @Override
        public EntityNameAccessor create() {
            try {
                NativeComponentMapper mapper = NativeComponentMapper.getInstance();
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                MethodHandle get = lookup.findVirtual(Entity.class, "customName",
                        MethodType.methodType(mapper.getComponentClass()));
                MethodHandle set = lookup.findVirtual(Entity.class, "customName",
                        MethodType.methodType(Void.TYPE, mapper.getComponentClass()));
                return new EntityNameAccessorImpl(mapper, get, set);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
