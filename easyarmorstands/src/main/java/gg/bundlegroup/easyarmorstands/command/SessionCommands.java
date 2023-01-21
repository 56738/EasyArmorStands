package gg.bundlegroup.easyarmorstands.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.processing.CommandContainer;
import cloud.commandframework.annotations.specifier.Greedy;
import gg.bundlegroup.easyarmorstands.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.platform.EasFeature;
import gg.bundlegroup.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.joml.Vector3d;

import java.util.Locale;

@CommandContainer
@CommandMethod("eas")
public class SessionCommands {
    @CommandMethod("visible <visible>")
    @CommandPermission("easyarmorstands.edit.visible")
    public void setVisible(EasCommandSender sender, Session session, @Argument("visible") boolean visible) {
        session.getEntity().setVisible(visible);
        if (visible) {
            sender.sendMessage(Component.text("Armor stand set to visible", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Armor stand set to invisible", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("baseplate <visible>")
    @CommandPermission("easyarmorstands.edit.baseplate")
    public void setBasePlate(EasCommandSender sender, Session session, @Argument("visible") boolean visible) {
        session.getEntity().setBasePlate(visible);
        if (visible) {
            sender.sendMessage(Component.text("Base plate enabled", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Base plate disabled", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("arms <visible>")
    @CommandPermission("easyarmorstands.edit.arms")
    public void setArms(EasCommandSender sender, Session session, @Argument("visible") boolean visible) {
        session.getEntity().setArms(visible);
        if (visible) {
            sender.sendMessage(Component.text("Arms enabled", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Arms disabled", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("gravity <enabled>")
    @CommandPermission("easyarmorstands.edit.gravity")
    public void setGravity(EasCommandSender sender, Session session, @Argument("enabled") boolean enabled) {
        session.getEntity().setGravity(enabled);
        if (session.getSkeleton() != null) {
            session.getSkeleton().setGravity(enabled);
        }
        if (enabled) {
            sender.sendMessage(Component.text("Gravity enabled", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Gravity disabled", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("size <size>")
    @CommandPermission("easyarmorstands.edit.size")
    public void setSize(EasCommandSender sender, Session session, @Argument("size") ArmorStandSize size) {
        session.getEntity().setSmall(size == ArmorStandSize.SMALL);
        if (session.getSkeleton() != null) {
            session.getSkeleton().setSmall(size == ArmorStandSize.SMALL);
        }
        sender.sendMessage(Component.text("Size changed to " + size.name().toLowerCase(Locale.ROOT), NamedTextColor.GREEN));
    }

    @CommandMethod("name [name]")
    @CommandPermission("easyarmorstands.edit.name")
    public void setName(EasCommandSender sender, Session session, @Argument("name") @Greedy String input) {
        Component name = MiniMessage.miniMessage().deserializeOrNull(input);
        session.getEntity().setCustomName(name);
        session.getEntity().setCustomNameVisible(name != null);
        sender.sendMessage(Component.text("Name tag changed", NamedTextColor.GREEN));
    }

    @CommandMethod("cantick <value>")
    @CommandPermission("easyarmorstands.edit.cantick")
    @RequiresFeature(EasFeature.ARMOR_STAND_CAN_TICK)
    public void setCanTick(EasCommandSender sender, Session session, @Argument("value") boolean canTick) {
        session.getEntity().setCanTick(canTick);
        if (canTick) {
            sender.sendMessage(Component.text("Armor stand ticking enabled", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Armor stand ticking disabled", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("glow <glowing>")
    @CommandPermission("easyarmorstands.edit.glow")
    @RequiresFeature(EasFeature.ENTITY_GLOW)
    public void setGlow(EasCommandSender sender, Session session, @Argument("glowing") boolean glowing) {
        session.getEntity().setGlowing(glowing);
        if (glowing) {
            sender.sendMessage(Component.text("Armor stand glowing enabled", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Armor stand glowing disabled", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("copy")
    @CommandPermission("easyarmorstands.copy")
    public void copy(EasCommandSender sender, Session session) {
        EasArmorStand entity = session.getEntity();
        entity.getWorld().spawnArmorStand(entity.getPosition(), entity.getYaw(), e -> {
            e.setVisible(entity.isVisible());
            e.setBasePlate(entity.hasBasePlate());
            e.setArms(entity.hasArms());
            e.setGravity(entity.hasGravity());
            e.setSmall(entity.isSmall());
            e.setCustomName(entity.getCustomName());
            e.setCustomNameVisible(entity.isCustomNameVisible());
            e.setCanTick(entity.canTick());
            Vector3d pose = new Vector3d();
            for (EasArmorStand.Part part : EasArmorStand.Part.values()) {
                e.setPose(part, entity.getPose(part, pose));
            }
            for (EasArmorEntity.Slot slot : EasArmorEntity.Slot.values()) {
                e.setItem(slot, entity.getItem(slot));
            }
            e.setGlowing(entity.isGlowing());
        });
        sender.sendMessage(Component.text("Duplicated the armor stand", NamedTextColor.GREEN));
        session.startMoving();
    }

    @CommandMethod("open")
    @CommandPermission("easyarmorstands.open")
    public void openEquipmentMenu(Session session) {
        session.openMenu();
    }
}
