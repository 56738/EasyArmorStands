package gg.bundlegroup.easyarmorstands.common.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.processing.CommandContainer;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.specifier.Range;
import gg.bundlegroup.easyarmorstands.common.bone.Bone;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.common.platform.EasFeature;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.tool.Tool;
import gg.bundlegroup.easyarmorstands.common.util.ArmorStandSnapshot;
import gg.bundlegroup.easyarmorstands.common.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.joml.Vector3d;
import org.joml.Vector3dc;

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
        sender.sendMessage(Component.text("Size changed to " +
                size.name().toLowerCase(Locale.ROOT), NamedTextColor.GREEN));
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

    @CommandMethod("lock <locked>")
    @CommandPermission("easyarmorstands.edit.lock")
    @RequiresFeature(EasFeature.ARMOR_STAND_LOCK)
    public void setLocked(EasCommandSender sender, Session session, @Argument("locked") boolean locked) {
        session.getEntity().setLocked(locked);
        if (locked) {
            sender.sendMessage(Component.text("Armor stand items locked", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Armor stand items unlocked", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("invulnerable <invulnerable>")
    @CommandPermission("easyarmorstands.edit.invulnerable")
    @RequiresFeature(EasFeature.ENTITY_INVULNERABLE)
    public void setInvulnerable(EasCommandSender sender, Session session, @Argument("invulnerable") boolean invulnerable) {
        session.getEntity().setInvulnerable(invulnerable);
        if (invulnerable) {
            sender.sendMessage(Component.text("Armor stand is invulnerable", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Armor stand is vulnerable", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("copy")
    @CommandPermission("easyarmorstands.copy")
    public void copy(EasCommandSender sender, Session session) {
        EasArmorStand entity = session.getEntity();
        new ArmorStandSnapshot(entity).spawn(entity.getWorld());
        sender.sendMessage(Component.text("Duplicated the armor stand", NamedTextColor.GREEN));
        session.startMoving();
    }

    @CommandMethod("open")
    @CommandPermission("easyarmorstands.open")
    public void openEquipmentMenu(Session session) {
        session.openMenu();
    }

    @CommandMethod("snap angle [value]")
    @CommandPermission("easyarmorstands.snap")
    public void setAngleSnapIncrement(
            EasCommandSender sender, Session session,
            @Argument(value = "value", defaultValue = "1.40625") @Range(min = "0", max = "90") double value) {
        session.setAngleSnapIncrement(value);
        sender.sendMessage(Component.text("Set angle snapping increment to " + value + "°", NamedTextColor.GREEN));
    }

    @CommandMethod("snap move [value]")
    @CommandPermission("easyarmorstands.snap")
    public void setSnapIncrement(
            EasCommandSender sender, Session session,
            @Argument(value = "value", defaultValue = "0.0625") @Range(min = "0", max = "10") double value) {
        session.setSnapIncrement(value);
        sender.sendMessage(Component.text("Set movement snapping increment to " + value, NamedTextColor.GREEN));
    }

    @CommandMethod("align [axis] [value] [offset]")
    @CommandPermission("easyarmorstands.align")
    public void align(
            EasCommandSender sender,
            Session session,
            @Argument(value = "axis", defaultValue = "all") AlignAxis axis,
            @Argument(value = "value") @Range(min = "0.001", max = "1") Double value,
            @Argument(value = "offset") @Range(min = "-1", max = "1") Double offset
    ) {
        Vector3d offsetVector = new Vector3d();
        if (value == null) {
            // None specified: Snap to the middle of the bottom of a block
            value = 1.0;
            offsetVector.set(0.5, 0.0, 0.5);
        } else if (offset != null) {
            offsetVector.set(offset, offset, offset);
        }
        Vector3dc position = axis.snap(session.getEntity().getPosition(), value, offsetVector, new Vector3d());
        if (!session.move(position)) {
            sender.sendMessage(Component.text("Unable to move", NamedTextColor.RED));
            return;
        }
        sender.sendMessage(Component.text()
                .content("Moved to ")
                .append(Component.text(Util.POSITION_FORMAT.format(position.x()), TextColor.color(0xFF7777)))
                .append(Component.text(", "))
                .append(Component.text(Util.POSITION_FORMAT.format(position.y()), TextColor.color(0x77FF77)))
                .append(Component.text(", "))
                .append(Component.text(Util.POSITION_FORMAT.format(position.z()), TextColor.color(0x7777FF)))
                .color(NamedTextColor.GREEN));
    }

    @CommandMethod("edit <bone>")
    @CommandPermission("easyarmorstands.edit.select")
    public void selectBone(
            EasCommandSender sender,
            Session session,
            @Argument("bone") Bone bone
    ) {
        session.setBone(bone);
        sender.sendMessage(Component.text()
                .content("Selected bone: ")
                .append(bone.subtitle())
                .color(NamedTextColor.GREEN));
    }

    @CommandMethod("edit <bone> <tool>")
    @CommandPermission("easyarmorstands.edit.select")
    public void selectTool(
            EasCommandSender sender,
            Session session,
            @Argument("bone") Bone bone,
            @Argument("tool") Tool tool
    ) {
        session.setBone(bone, tool);
        sender.sendMessage(Component.text()
                .content("Selected bone: ")
                .append(bone.subtitle())
                .append(Component.text(": "))
                .append(tool.component())
                .color(NamedTextColor.GREEN));
    }
}
