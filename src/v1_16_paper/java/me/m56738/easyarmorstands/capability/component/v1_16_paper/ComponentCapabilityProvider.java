package me.m56738.easyarmorstands.capability.component.v1_16_paper;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;

public class ComponentCapabilityProvider implements CapabilityProvider<ComponentCapability> {
    @Override
    public boolean isSupported() {
        NativeComponentMapper mapper = NativeComponentMapper.getInstance();
        if (mapper == null) {
            return false;
        }
        try {
            Entity.class.getMethod("customName");
            Entity.class.getMethod("customName", mapper.getComponentClass());
            ItemMeta.class.getMethod("displayName", mapper.getComponentClass());
            ItemMeta.class.getMethod("lore", List.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public ComponentCapability create(Plugin plugin) {
        try {
            NativeComponentMapper mapper = NativeComponentMapper.getInstance();
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle getCustomName = lookup.findVirtual(Entity.class, "customName",
                    MethodType.methodType(mapper.getComponentClass()));
            MethodHandle setCustomName = lookup.findVirtual(Entity.class, "customName",
                    MethodType.methodType(void.class, mapper.getComponentClass()));
            MethodHandle setDisplayName = lookup.findVirtual(ItemMeta.class, "displayName",
                    MethodType.methodType(void.class, mapper.getComponentClass()));
            MethodHandle setLore = lookup.findVirtual(ItemMeta.class, "lore",
                    MethodType.methodType(void.class, List.class));
            return new ComponentCapabilityImpl(mapper, getCustomName, setCustomName, setDisplayName, setLore);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ComponentCapabilityImpl implements ComponentCapability {
        private final NativeComponentMapper mapper;
        private final MethodHandle getCustomName;
        private final MethodHandle setCustomName;
        private final MethodHandle setDisplayName;
        private final MethodHandle setLore;

        public ComponentCapabilityImpl(NativeComponentMapper mapper, MethodHandle getCustomName, MethodHandle setCustomName, MethodHandle setDisplayName, MethodHandle setLore) {
            this.mapper = mapper;
            this.getCustomName = getCustomName;
            this.setCustomName = setCustomName;
            this.setDisplayName = setDisplayName;
            this.setLore = setLore;
        }

        @Override
        public Component getCustomName(Entity entity) {
            try {
                return mapper.convertFromNative(getCustomName.invoke(entity));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void setCustomName(Entity entity, Component name) {
            try {
                setCustomName.invoke(entity, mapper.convertToNative(name));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void setDisplayName(ItemMeta meta, Component displayName) {
            try {
                setDisplayName.invoke(meta, mapper.convertToNative(
                        displayName.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void setLore(ItemMeta meta, List<Component> lore) {
            List<Object> nativeLore = new ArrayList<>(lore.size());
            for (Component component : lore) {
                nativeLore.add(mapper.convertToNative(
                        component.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)));
            }
            try {
                setLore.invoke(meta, nativeLore);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
