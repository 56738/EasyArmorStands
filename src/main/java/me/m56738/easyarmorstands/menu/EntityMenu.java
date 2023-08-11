package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EntityMenu<T extends Entity> extends SessionMenu {
    private final Session session;
    private final T entity;

    public EntityMenu(Session session, T entity) {
        super(6, PlainTextComponentSerializer.builder()
                        .flattener(EasyArmorStands.getInstance().getAdventure().flattener())
                        .build()
                        .serialize(EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(entity.getType())),
                session);
        this.session = session;
        this.entity = entity;
    }

    @Override
    public void initialize() {
        // TODO
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

    public @NotNull T getEntity() {
        return entity;
    }

    public boolean hasEquipment() {
        return entity instanceof LivingEntity;
    }
}
