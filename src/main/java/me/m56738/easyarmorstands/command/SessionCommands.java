package me.m56738.easyarmorstands.command;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandDescription;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Greedy;
import cloud.commandframework.annotations.specifier.Range;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.bone.Bone;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.invulnerability.InvulnerabilityCapability;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.history.DestroyArmorStandAction;
import me.m56738.easyarmorstands.session.ArmorStandSession;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionManager;
import me.m56738.easyarmorstands.tool.Tool;
import me.m56738.easyarmorstands.util.ArmorStandSnapshot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
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
    @CommandPermission("easyarmorstands.property.visible")
    @CommandDescription("Change the visibility of an armor stand")
    public void setVisible(CommandSender sender,
                           Audience audience,
                           ArmorStandSession session,
                           @Argument("visible") boolean visible) {
        session.getEntity().setVisible(visible);
        session.commit();
        if (visible) {
            audience.sendMessage(Component.text("Armor stand set to visible", NamedTextColor.GREEN));
        } else {
            audience.sendMessage(Component.text("Armor stand set to invisible", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("baseplate <visible>")
    @CommandPermission("easyarmorstands.property.baseplate")
    @CommandDescription("Change the visibility of the base plate")
    public void setBasePlate(CommandSender sender,
                             Audience audience,
                             ArmorStandSession session,
                             @Argument("visible") boolean visible) {
        session.getEntity().setBasePlate(visible);
        session.commit();
        if (visible) {
            audience.sendMessage(Component.text("Base plate enabled", NamedTextColor.GREEN));
        } else {
            audience.sendMessage(Component.text("Base plate disabled", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("arms <visible>")
    @CommandPermission("easyarmorstands.property.arms")
    @CommandDescription("Change the visibility of the arms")
    public void setArms(CommandSender sender,
                        Audience audience,
                        ArmorStandSession session,
                        @Argument("visible") boolean visible) {
        session.getEntity().setArms(visible);
        session.commit();
        if (visible) {
            audience.sendMessage(Component.text("Arms enabled", NamedTextColor.GREEN));
        } else {
            audience.sendMessage(Component.text("Arms disabled", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("gravity <enabled>")
    @CommandPermission("easyarmorstands.property.gravity")
    @CommandDescription("Toggle gravity for an armor stand")
    public void setGravity(CommandSender sender,
                           Audience audience,
                           ArmorStandSession session,
                           @Argument("enabled") boolean enabled) {
        session.getEntity().setGravity(enabled);
        session.commit();
        if (enabled) {
            audience.sendMessage(Component.text("Gravity enabled", NamedTextColor.GREEN));
        } else {
            audience.sendMessage(Component.text("Gravity disabled", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("size <size>")
    @CommandPermission("easyarmorstands.property.size")
    @CommandDescription("Change the size of an armor stand")
    public void setSize(CommandSender sender,
                        Audience audience,
                        ArmorStandSession session,
                        @Argument("size") ArmorStandSize size) {
        session.getEntity().setSmall(size == ArmorStandSize.SMALL);
        session.commit();
        audience.sendMessage(Component.text("Size changed to " +
                size.name().toLowerCase(Locale.ROOT), NamedTextColor.GREEN));
    }

    @CommandMethod("name [name]")
    @CommandPermission("easyarmorstands.property.name")
    @CommandDescription("Change the name of an armor stand")
    public void setName(CommandSender sender,
                        Audience audience,
                        ArmorStandSession session,
                        ComponentCapability capability,
                        @Argument(value = "name", description = "MiniMessage text") @Greedy String input) {
        setName(audience, session, capability, input, MiniMessage.miniMessage());
        if (input != null && (input.contains("&") || input.contains("§"))) {
            audience.sendMessage(Component.text()
                    .content("This command uses the ")
                    .append(Component.text()
                            .content("MiniMessage")
                            .hoverEvent(Component.text("Open documentation"))
                            .clickEvent(ClickEvent.openUrl("https://docs.advntr.dev/minimessage/format.html"))
                            .decorate(TextDecoration.UNDERLINED)
                    )
                    .append(Component.text(" format. Use "))
                    .append(Component.text()
                            .content("/eas lname")
                            .hoverEvent(Component.text("/eas lname " + input))
                            .clickEvent(ClickEvent.runCommand("/eas lname " + input))
                            .decorate(TextDecoration.UNDERLINED)
                    )
                    .append(Component.text(" with legacy (&c) color codes."))
                    .color(NamedTextColor.GRAY)
            );
        }
    }

    @CommandMethod("lname [name]")
    @CommandPermission("easyarmorstands.property.name")
    @CommandDescription("Change the name of an armor stand")
    public void setLegacyName(CommandSender sender,
                              Audience audience,
                              ArmorStandSession session,
                              ComponentCapability capability,
                              @Argument(value = "name", description = "Legacy text (&c)") @Greedy String input) {
        setName(audience, session, capability, input, LegacyComponentSerializer.legacyAmpersand());
    }

    private void setName(Audience audience,
                         ArmorStandSession session,
                         ComponentCapability capability,
                         String input, ComponentSerializer<?, ? extends Component, String> serializer) {
        Component name = serializer.deserializeOrNull(input);
        capability.setCustomName(session.getEntity(), name);
        session.getEntity().setCustomNameVisible(name != null);
        session.commit();
        if (name != null) {
            audience.sendMessage(Component.text()
                    .append(Component.text("Name tag changed to ", NamedTextColor.GREEN))
                    .append(name)
                    .hoverEvent(Component.text(input)));
        } else {
            audience.sendMessage(Component.text("Name tag cleared", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("cantick <value>")
    @CommandPermission("easyarmorstands.property.cantick")
    @CommandDescription("Change whether ticking is enabled for an armor stand")
    public void setCanTick(CommandSender sender,
                           Audience audience,
                           ArmorStandSession session,
                           TickCapability tickCapability,
                           @Argument("value") boolean canTick) {
        tickCapability.setCanTick(session.getEntity(), canTick);
        session.commit();
        if (canTick) {
            audience.sendMessage(Component.text("Armor stand ticking enabled", NamedTextColor.GREEN));
        } else {
            audience.sendMessage(Component.text("Armor stand ticking disabled", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("glow <glowing>")
    @CommandPermission("easyarmorstands.property.glow")
    @CommandDescription("Change whether an armor stand is glowing (outline is visible)")
    public void setGlow(CommandSender sender,
                        Audience audience,
                        ArmorStandSession session,
                        GlowCapability glowCapability,
                        @Argument("glowing") boolean glowing) {
        glowCapability.setGlowing(session.getEntity(), glowing);
        session.commit();
        if (glowing) {
            audience.sendMessage(Component.text("Armor stand glowing enabled", NamedTextColor.GREEN));
        } else {
            audience.sendMessage(Component.text("Armor stand glowing disabled", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("lock <locked>")
    @CommandPermission("easyarmorstands.property.lock")
    @CommandDescription("Change whether the equipment of an armor stand is locked")
    public void setLocked(CommandSender sender,
                          Audience audience,
                          ArmorStandSession session,
                          LockCapability lockCapability,
                          @Argument("locked") boolean locked) {
        lockCapability.setLocked(session.getEntity(), locked);
        session.commit();
        if (locked) {
            audience.sendMessage(Component.text("Armor stand items locked", NamedTextColor.GREEN));
        } else {
            audience.sendMessage(Component.text("Armor stand items unlocked", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("invulnerable <invulnerable>")
    @CommandPermission("easyarmorstands.property.invulnerable")
    @CommandDescription("Change whether an armor stand is invulnerable")
    public void setInvulnerable(CommandSender sender,
                                Audience audience,
                                ArmorStandSession session,
                                InvulnerabilityCapability invulnerabilityCapability,
                                @Argument("invulnerable") boolean invulnerable) {
        invulnerabilityCapability.setInvulnerable(session.getEntity(), invulnerable);
        session.commit();
        if (invulnerable) {
            audience.sendMessage(Component.text("Armor stand is invulnerable", NamedTextColor.GREEN));
        } else {
            audience.sendMessage(Component.text("Armor stand is vulnerable", NamedTextColor.GREEN));
        }
    }

    @CommandMethod("clone")
    @CommandPermission("easyarmorstands.clone")
    @CommandDescription("Place a copy of the armor stand")
    public void clone(CommandSender sender,
                      Audience audience,
                      ArmorStandSession session) {
        ArmorStand entity = session.getEntity();
        new ArmorStandSnapshot(entity).spawn();
        audience.sendMessage(Component.text("Cloned the armor stand", NamedTextColor.GREEN));
        session.startMoving(null);
    }

    @CommandMethod("spawn")
    @CommandPermission("easyarmorstands.spawn")
    @CommandDescription("Spawn an armor stand and start editing it")
    public void spawn(Player player) {
        sessionManager.spawnAndStart(player);
    }

    @CommandMethod("destroy")
    @CommandPermission("easyarmorstands.destroy")
    @CommandDescription("Destroy the selected armor stand")
    public void destroy(ArmorStandSession session, Audience audience) {
        ArmorStand entity = session.getEntity();
        Player player = session.getPlayer();
        sessionManager.stop(player);
        EasyArmorStands.getInstance().getHistory(player).push(new DestroyArmorStandAction(entity));
        entity.remove();
        audience.sendMessage(Component.text("Armor stand destroyed", NamedTextColor.GREEN));
    }

    @CommandMethod("open")
    @CommandPermission("easyarmorstands.open")
    @CommandDescription("Open the menu")
    public void openMenu(ArmorStandSession session) {
        session.openMenu();
    }

    @CommandMethod("snap angle [value]")
    @CommandPermission("easyarmorstands.snap")
    @CommandDescription("Change the angle snapping increment")
    public void setAngleSnapIncrement(
            CommandSender sender,
            Audience audience,
            Session session,
            @Argument(value = "value") @Range(min = "0", max = "90") Double value) {
        if (value == null) {
            value = Session.DEFAULT_ANGLE_SNAP_INCREMENT;
            if (value == session.getAngleSnapIncrement()) {
                value = 0.0;
            }
        }
        session.setAngleSnapIncrement(value);
        audience.sendMessage(Component.text("Set angle snapping increment to " + value + "°", NamedTextColor.GREEN));
    }

    @CommandMethod("snap move [value]")
    @CommandPermission("easyarmorstands.snap")
    @CommandDescription("Change the movement snapping increment")
    public void setSnapIncrement(
            CommandSender sender,
            Audience audience,
            Session session,
            @Argument(value = "value") @Range(min = "0", max = "10") Double value) {
        if (value == null) {
            value = Session.DEFAULT_SNAP_INCREMENT;
            if (value == session.getSnapIncrement()) {
                value = 0.0;
            }
        }
        session.setSnapIncrement(value);
        audience.sendMessage(Component.text("Set movement snapping increment to " + value, NamedTextColor.GREEN));
    }

    @CommandMethod("align [axis] [value] [offset]")
    @CommandPermission("easyarmorstands.align")
    @CommandDescription("Move an armor stand to the middle of the block")
    public void align(
            CommandSender sender,
            Audience audience,
            ArmorStandSession session,
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
        Vector3dc position = axis.snap(Util.toVector3d(session.getEntity().getLocation()), value, offsetVector, new Vector3d());
        if (!session.move(position)) {
            audience.sendMessage(Component.text("Unable to move", NamedTextColor.RED));
            return;
        }
        session.commit();
        audience.sendMessage(Component.text()
                .content("Moved to ")
                .append(Component.text(Util.POSITION_FORMAT.format(position.x()), TextColor.color(0xFF7777)))
                .append(Component.text(", "))
                .append(Component.text(Util.POSITION_FORMAT.format(position.y()), TextColor.color(0x77FF77)))
                .append(Component.text(", "))
                .append(Component.text(Util.POSITION_FORMAT.format(position.z()), TextColor.color(0x7777FF)))
                .color(NamedTextColor.GREEN));
    }

    @CommandMethod("edit <bone>")
    @CommandPermission("easyarmorstands.edit")
    @CommandDescription("Select a bone of an armor stand")
    public void selectBone(
            CommandSender sender,
            Audience audience,
            Session session,
            @Argument("bone") Bone bone
    ) {
        session.setBone(bone);
        audience.sendMessage(Component.text()
                .content("Selected bone: ")
                .append(bone.getName())
                .color(NamedTextColor.GREEN));
    }

    @CommandMethod("edit <bone> <tool>")
    @CommandPermission("easyarmorstands.edit")
    @CommandDescription("Select a bone of an armor stand and start editing it using a tool")
    public void selectTool(
            CommandSender sender,
            Audience audience,
            Session session,
            @Argument("bone") Bone bone,
            @Argument("tool") Tool tool
    ) {
        session.setBone(bone, tool, tool.getTarget());
        audience.sendMessage(Component.text()
                .content("Selected bone: ")
                .append(bone.getName())
                .append(Component.text(": "))
                .append(tool.getName())
                .color(NamedTextColor.GREEN));
    }
}
