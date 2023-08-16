package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.event.EntityElementMenuInitializeEvent;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.node.Button;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.node.SimpleEntityButton;
import me.m56738.easyarmorstands.node.SimpleEntityNode;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class SimpleEntityElement<E extends Entity> implements ConfigurableEntityElement<E>, SelectableElement, MenuElement, DestroyableElement {
    private final E entity;
    private final SimpleEntityElementType<E> type;
    private final PropertyRegistry properties = new Properties();

    public SimpleEntityElement(E entity, SimpleEntityElementType<E> type) {
        this.entity = entity;
        this.type = type;
    }

    @Override
    public E getEntity() {
        return entity;
    }

    @Override
    public SimpleEntityElementType<E> getType() {
        return type;
    }

    @Override
    public EntityElementReference<E> getReference() {
        return new EntityElementReference<>(type, entity.getUniqueId());
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public PropertyRegistry getProperties() {
        return properties;
    }

    @Override
    public Button createButton(Session session) {
        return new SimpleEntityButton(session, entity);
    }

    @Override
    public Node createNode(Session session) {
        return new SimpleEntityNode(session, Component.text("Editing an entity"), this);
    }

    @Override
    public void openMenu(EasPlayer player) {
        SplitMenuBuilder builder = new SplitMenuBuilder();
        PropertyContainer container = PropertyContainer.tracked(player, this);
        Component title = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(entity.getType());
        populateMenu(player, builder, container);
        Bukkit.getPluginManager().callEvent(new EntityElementMenuInitializeEvent(player.get(), this, builder, container, title));
        player.get().openInventory(builder.build(title).getInventory());
    }

    @Override
    public boolean hasItemSlots() {
        return entity instanceof LivingEntity;
    }

    protected void populateMenu(EasPlayer player, SplitMenuBuilder builder, PropertyContainer container) {
    }

    @Override
    public void destroy() {
        entity.remove();
    }

    private class Properties extends PropertyRegistry {
        @Override
        public boolean isValid() {
            return entity.isValid();
        }
    }
}
