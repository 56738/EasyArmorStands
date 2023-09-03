package me.m56738.easyarmorstands.capability.component.v1_16_paper;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.util.NativeComponentMapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
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
            Bukkit.class.getMethod("createInventory", InventoryHolder.class, int.class, Component.class);
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
            return new ComponentCapabilityImpl();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ComponentCapabilityImpl implements ComponentCapability {
        private static final Style fallbackStyle = Style.style(
                NamedTextColor.WHITE,
                TextDecoration.ITALIC.withState(false));

        private final NativeComponentMapper mapper;
        private final MethodHandle getCustomName;
        private final MethodHandle setCustomName;
        private final MethodHandle setDisplayName;
        private final MethodHandle setLore;
        private final MethodHandle getItemDisplayName;

        public ComponentCapabilityImpl() throws NoSuchMethodException, IllegalAccessException {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            mapper = NativeComponentMapper.getInstance();
            getCustomName = lookup.findVirtual(Entity.class, "customName",
                    MethodType.methodType(mapper.getComponentClass()));
            setCustomName = lookup.findVirtual(Entity.class, "customName",
                    MethodType.methodType(void.class, mapper.getComponentClass()));
            setDisplayName = lookup.findVirtual(ItemMeta.class, "displayName",
                    MethodType.methodType(void.class, mapper.getComponentClass()));
            setLore = lookup.findVirtual(ItemMeta.class, "lore",
                    MethodType.methodType(void.class, List.class));
            getItemDisplayName = lookup.findVirtual(ItemStack.class, "displayName",
                    MethodType.methodType(mapper.getComponentClass()));
        }

        private static Component style(Component component) {
            if (component == null) {
                return null;
            }
            return component.applyFallbackStyle(fallbackStyle);
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
                setDisplayName.invoke(meta, mapper.convertToNative(style(displayName)));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void setLore(ItemMeta meta, List<Component> lore) {
            List<Object> nativeLore = new ArrayList<>(lore.size());
            for (Component component : lore) {
                nativeLore.add(mapper.convertToNative(style(component)));
            }
            try {
                setLore.invoke(meta, nativeLore);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Component getItemDisplayName(ItemStack item) {
            try {
                return mapper.convertFromNative(getItemDisplayName.invoke(item));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Inventory createInventory(InventoryHolder holder, int size, Component title) {
            return Bukkit.createInventory(holder, size, title);
        }
    }
}
