package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.event.EntityObjectMenuInitializeEvent;
import me.m56738.easyarmorstands.history.action.EntityDestroyAction;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.EditableObjectNode;
import me.m56738.easyarmorstands.session.EntitySpawner;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class SimpleEntityObject extends AbstractEditableObject implements EntityObject, DestroyableObject, MenuObject {
    private final Entity entity;

    public SimpleEntityObject(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public boolean hasItemSlots() {
        return entity instanceof LivingEntity;
    }

    @Override
    public EditableObjectReference asReference() {
        return new EntityObjectReference(entity.getUniqueId());
    }

    @Override
    public Button createButton(Session session) {
        return new SimpleEntityButton(session, entity);
    }

    @Override
    public EditableObjectNode createNode(Session session) {
        Component name = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(entity.getType());
        return new SimpleEntityNode(session, name, this);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public boolean destroy(Player player) {
        EntityDestroyAction<?> action = new EntityDestroyAction<>(entity);
        if (!EntitySpawner.tryRemove(entity, player)) {
            return false;
        }
        EasyArmorStands.getInstance().getHistory(player).push(action);
        return true;
    }

    protected void populateMenu(Player player, SplitMenuBuilder builder) {
    }

    @Override
    public void openMenu(Player player) {
        SplitMenuBuilder builder = new SplitMenuBuilder();
        populateMenu(player, builder);
        Component title = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(entity.getType());
        Bukkit.getPluginManager().callEvent(new EntityObjectMenuInitializeEvent(player, this, builder, title));
        player.openInventory(builder.build(title).getInventory());
    }
}
