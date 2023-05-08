package me.m56738.easyarmorstands.node.v1_19_4;

import cloud.commandframework.arguments.parser.ArgumentParser;
import me.m56738.easyarmorstands.node.ValueNode;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.BlockDisplay;
import org.jetbrains.annotations.Nullable;

public class BlockDisplayRootNode extends DisplayRootNode implements ValueNode<BlockData> {
    private final Session session;
    private final BlockDisplay entity;

    public BlockDisplayRootNode(Session session, Component name, BlockDisplay entity) {
        super(session, name, entity);
        this.session = session;
        this.entity = entity;
    }

    @Override
    public BlockDisplay getEntity() {
        return entity;
    }

    @Override
    public Component getName() {
        return Component.text("Block");
    }

    @Override
    public Component getValueComponent(BlockData value) {
        String key = value.getMaterial().getBlockTranslationKey();
        Component component;
        if (key != null) {
            component = Component.translatable(key);
        } else {
            component = Component.text(value.getMaterial().getKey().toString());
        }
        return component.hoverEvent(Component.text(value.getAsString()));
    }

    @Override
    public ArgumentParser<CommandSender, BlockData> getParser() {
        return new BlockDataArgumentParser<>();
    }

    @Override
    public void setValue(BlockData value) {
        entity.setBlock(value);
    }

    @Override
    public @Nullable String getValuePermission() {
        return "easyarmorstands.set.display.block";
    }
}
