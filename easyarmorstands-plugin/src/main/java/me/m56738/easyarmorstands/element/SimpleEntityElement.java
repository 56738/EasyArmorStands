package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.EntityElementReference;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.event.menu.EntityElementMenuInitializeEvent;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.node.SimpleEntityButton;
import me.m56738.easyarmorstands.node.SimpleEntityNode;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

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
    public @NotNull SimpleEntityElementType<E> getType() {
        return type;
    }

    @Override
    public @NotNull EntityElementReference<E> getReference() {
        return new EntityElementReferenceImpl<>(type, entity.getUniqueId());
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public @NotNull PropertyRegistry getProperties() {
        return properties;
    }

    @Override
    public Button createButton(Session session) {
        return new SimpleEntityButton(session, entity);
    }

    @Override
    public Node createNode(Session session) {
        return new SimpleEntityNode(session, Message.component("easyarmorstands.node.select-axis"), this);
    }

    @Override
    public void openMenu(Player player) {
        EasPlayer easPlayer = new EasPlayer(player);
        Locale locale = easPlayer.pointers().getOrDefault(Identity.LOCALE, Locale.US);
        SplitMenuBuilder builder = new SplitMenuBuilder();
        PropertyContainer container = new TrackedPropertyContainer(this, easPlayer);
        Component title = EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(entity.getType());
        populateMenu(easPlayer, builder, container);
        Bukkit.getPluginManager().callEvent(new EntityElementMenuInitializeEvent(player, locale, this, builder, container, title));
        player.openInventory(builder.build(title, locale).getInventory());
    }

    @Override
    public boolean hasItemSlots() {
        return entity instanceof LivingEntity;
    }

    protected void populateMenu(EasPlayer player, MenuBuilder builder, PropertyContainer container) {
    }

    @Override
    public void destroy() {
        entity.remove();
    }

    @Override
    public @NotNull Component getName() {
        return Component.text(Util.getId(entity.getUniqueId()));
    }

    private class Properties extends PropertyRegistry {
        @Override
        public boolean isValid() {
            return entity.isValid();
        }
    }
}
