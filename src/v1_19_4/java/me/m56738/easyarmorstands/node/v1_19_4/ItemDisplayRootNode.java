package me.m56738.easyarmorstands.node.v1_19_4;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.bukkit.data.ProtoItemStack;
import cloud.commandframework.bukkit.parsers.ItemStackArgument;
import me.m56738.easyarmorstands.menu.EntityMenu;
import me.m56738.easyarmorstands.node.ClickContext;
import me.m56738.easyarmorstands.node.ClickType;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public class ItemDisplayRootNode extends DisplayRootNode implements ValueNode<ProtoItemStack> {
    private final Session session;
    private final ItemDisplay entity;

    public ItemDisplayRootNode(Session session, Component name, ItemDisplay entity) {
        super(session, name, entity);
        this.session = session;
        this.entity = entity;
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        if (context.getType() == ClickType.LEFT_CLICK) {
            session.getPlayer().openInventory(new EntityMenu<>(6, "EasyArmorStands: Item display", session, entity).getInventory());
            return true;
        }

        return super.onClick(eyes, target, context);
    }

    @Override
    public ItemDisplay getEntity() {
        return entity;
    }

    @Override
    public Component getName() {
        return Component.text("Item");
    }

    @Override
    public Component getValueComponent(ProtoItemStack value) {
        String key = value.material().getTranslationKey();
        return Component.translatable(key);
    }

    @Override
    public ArgumentParser<CommandSender, ProtoItemStack> getParser() {
        return new ItemStackArgument.Parser<>();
    }

    @Override
    public void setValue(ProtoItemStack value) {
        entity.setItemStack(value.createItemStack(1, true));
    }

    @Override
    public @Nullable String getValuePermission() {
        return "easyarmorstands.set.display.item";
    }
}
