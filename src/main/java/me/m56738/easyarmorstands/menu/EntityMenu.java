package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.event.SessionMenuInitializeEvent;
import me.m56738.easyarmorstands.inventory.DisabledSlot;
import me.m56738.easyarmorstands.inventory.InventoryMenu;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.property.ButtonEntityProperty;
import me.m56738.easyarmorstands.property.EntityProperty;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EntityMenu<T extends Entity> extends InventoryMenu {
    private final Session session;
    private final T entity;

    public EntityMenu(Session session, T entity) {
        super(6, PlainTextComponentSerializer.builder()
                .flattener(EasyArmorStands.getInstance().getAdventure().flattener())
                .build()
                .serialize(EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(entity.getType())));
        this.session = session;
        this.entity = entity;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void initialize() {
        List<InventorySlot> pending = new ArrayList<>();
        for (EntityProperty property : EasyArmorStands.getInstance().getEntityPropertyRegistry().getProperties(entity.getClass()).values()) {
            if (property instanceof ButtonEntityProperty && property.isSupported(entity)) {
                ButtonEntityProperty buttonProperty = (ButtonEntityProperty) property;
                InventorySlot slot = buttonProperty.createSlot(this);
                int index = buttonProperty.getSlotIndex();
                if (index == -1) {
                    pending.add(slot);
                } else {
                    setSlot(index, slot);
                }
            }
        }
        for (InventorySlot slot : pending) {
            addButton(slot);
        }
        Bukkit.getPluginManager().callEvent(new SessionMenuInitializeEvent(this));
        setEmptySlots(new DisabledSlot(this, ItemType.LIGHT_BLUE_STAINED_GLASS_PANE));
    }

    public void addButton(InventorySlot slot) {
        addShortcut(slot);
    }

    public void addShortcut(InventorySlot slot) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                setSlot(i, slot);
                return;
            }
        }
        throw new IllegalStateException("No space left in the menu");
    }

    public @NotNull Session getSession() {
        return session;
    }

    public @NotNull T getEntity() {
        return entity;
    }
}
