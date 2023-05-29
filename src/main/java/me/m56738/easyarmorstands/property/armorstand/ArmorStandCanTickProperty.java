package me.m56738.easyarmorstands.property.armorstand;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.BooleanArgument;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.property.EntityProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandCanTickProperty implements EntityProperty<ArmorStand, Boolean> {
    private final TickCapability tickCapability;

    public ArmorStandCanTickProperty(TickCapability tickCapability) {
        this.tickCapability = tickCapability;
    }

    @Override
    public Boolean getValue(ArmorStand entity) {
        return tickCapability.canTick(entity);
    }

    @Override
    public TypeToken<Boolean> getValueType() {
        return TypeToken.get(Boolean.class);
    }

    @Override
    public void setValue(ArmorStand entity, Boolean value) {
        tickCapability.setCanTick(entity, value);
    }

    @Override
    public @NotNull String getName() {
        return "cantick";
    }

    @Override
    public @NotNull Class<ArmorStand> getEntityType() {
        return ArmorStand.class;
    }

    @Override
    public ArgumentParser<EasCommandSender, Boolean> getArgumentParser() {
        return new BooleanArgument.BooleanParser<>(true);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("ticking");
    }

    @Override
    public @NotNull Component getValueName(Boolean value) {
        return value
                ? Component.text("enabled", NamedTextColor.GREEN)
                : Component.text("disabled", NamedTextColor.RED);
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.armorstand.cantick";
    }

}
