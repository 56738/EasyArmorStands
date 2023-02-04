package me.m56738.easyarmorstands.core.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.specifier.Range;
import me.m56738.easyarmorstands.core.bone.Bone;
import me.m56738.easyarmorstands.core.platform.EasArmorStand;
import me.m56738.easyarmorstands.core.platform.EasCommandSender;
import me.m56738.easyarmorstands.core.platform.EasFeature;
import me.m56738.easyarmorstands.core.platform.EasPlayer;
import me.m56738.easyarmorstands.core.session.Session;
import me.m56738.easyarmorstands.core.session.SessionManager;
import me.m56738.easyarmorstands.core.tool.Tool;
import me.m56738.easyarmorstands.core.util.ArmorStandSnapshot;
import me.m56738.easyarmorstands.core.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Locale;

@CommandMethod("eas")
public class SessionCommands {
    private final SessionManager sessionManager;

    public SessionCommands(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @CommandMethod("visible <visible>")
    @CommandPermission("easyarmorstands.edit.visible")
    @CommandDescription("Change the visibility of an armor stand")
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
    @CommandDescription("Change the visibility of the base plate")
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
    @CommandDescription("Change the visibility of the arms")
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
    @CommandDescription("Toggle gravity for an armor stand")
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
    @CommandDescription("Change the size of an armor stand")
    public void setSize(EasCommandSender sender, Session session, @Argument("size") ArmorStandSize size) {
        session.getEntity().setSmall(size == ArmorStandSize.SMALL);
        sender.sendMessage(Component.text("Size changed to " +
                size.name().toLowerCase(Locale.ROOT), NamedTextColor.GREEN));
    }

    @CommandMethod("name [name]")
    @CommandPermission("easyarmorstands.edit.name")
    @CommandDescription("Change the name of an armor stand")
    public void setName(EasCommandSender sender, Session session,
                        @Argument(value = "name", description = "MiniMessage text") @Greedy String input) {
        Component name = MiniMessage.miniMessage().deserializeOrNull(input);
        session.getEntity().setCustomName(name);
        session.getEntity().setCustomNameVisible(name != null);
        sender.sendMessage(Component.text("Name tag changed", NamedTextColor.GREEN));
    }

    @CommandMethod("cantick <value>")
    @CommandPermission("easyarmorstands.edit.cantick")
    @CommandDescription("Change whether ticking is enabled for an armor stand")
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
    @CommandDescription("Change whether an armor stand is glowing (outline is visible)")
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
    @CommandDescription("Change whether the equipment of an armor stand is locked")
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
    @CommandDescription("Change whether an armor stand is invulnerable")
    @RequiresFeature(EasFeature.ENTITY_INVULNERABLE)
    public void setInvulnerable(EasCommandSender sender, Session session, @Argument("invulnerable") boolean invulnerable) {
        session.getEntity().setInvulnerable(invulnerable);
        if (invulnerable) {
            sender.sendMessage(Component.text("Armor stand is invulnerable", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("Armor stand is vulnerable", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("clone")
    @CommandPermission("easyarmorstands.clone")
    @CommandDescription("Place a copy of the armor stand")
    public void clone(EasCommandSender sender, Session session) {
        EasArmorStand entity = session.getEntity();
        new ArmorStandSnapshot(entity).spawn(entity.getWorld());
        sender.sendMessage(Component.text("Cloned the armor stand", NamedTextColor.GREEN));
        session.startMoving(null);
    }

    @CommandMethod("spawn")
    @CommandPermission("easyarmorstands.spawn")
    @CommandDescription("Spawn an armor stand and start editing it")
    public void spawn(EasPlayer player) {
        sessionManager.spawnAndStart(player);
    }

    @CommandMethod("open")
    @CommandPermission("easyarmorstands.open")
    @CommandDescription("Open the menu")
    public void openMenu(Session session) {
        session.openMenu();
    }

    @CommandMethod("snap angle [value]")
    @CommandPermission("easyarmorstands.snap")
    @CommandDescription("Change the angle snapping increment")
    public void setAngleSnapIncrement(
            EasCommandSender sender, Session session,
            @Argument(value = "value") @Range(min = "0", max = "90") Double value) {
        if (value == null) {
            value = Session.DEFAULT_ANGLE_SNAP_INCREMENT;
            if (value == session.getAngleSnapIncrement()) {
                value = 0.0;
            }
        }
        session.setAngleSnapIncrement(value);
        sender.sendMessage(Component.text("Set angle snapping increment to " + value + "°", NamedTextColor.GREEN));
    }

    @CommandMethod("snap move [value]")
    @CommandPermission("easyarmorstands.snap")
    @CommandDescription("Change the movement snapping increment")
    public void setSnapIncrement(
            EasCommandSender sender, Session session,
            @Argument(value = "value") @Range(min = "0", max = "10") Double value) {
        if (value == null) {
            value = Session.DEFAULT_SNAP_INCREMENT;
            if (value == session.getSnapIncrement()) {
                value = 0.0;
            }
        }
        session.setSnapIncrement(value);
        sender.sendMessage(Component.text("Set movement snapping increment to " + value, NamedTextColor.GREEN));
    }

    @CommandMethod("align [axis] [value] [offset]")
    @CommandPermission("easyarmorstands.align")
    @CommandDescription("Move an armor stand to the middle of the block")
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
    @CommandDescription("Select a bone of an armor stand")
    public void selectBone(
            EasCommandSender sender,
            Session session,
            @Argument("bone") Bone bone
    ) {
        session.setBone(bone);
        sender.sendMessage(Component.text()
                .content("Selected bone: ")
                .append(bone.getName())
                .color(NamedTextColor.GREEN));
    }

    @CommandMethod("edit <bone> <tool>")
    @CommandPermission("easyarmorstands.edit.select")
    @CommandDescription("Select a bone of an armor stand and start editing it using a tool")
    public void selectTool(
            EasCommandSender sender,
            Session session,
            @Argument("bone") Bone bone,
            @Argument("tool") Tool tool
    ) {
        session.setBone(bone, tool, tool.getTarget());
        sender.sendMessage(Component.text()
                .content("Selected bone: ")
                .append(bone.getName())
                .append(Component.text(": "))
                .append(tool.getName())
                .color(NamedTextColor.GREEN));
    }
}
