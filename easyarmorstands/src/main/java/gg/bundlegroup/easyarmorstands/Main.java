package gg.bundlegroup.easyarmorstands;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import gg.bundlegroup.easyarmorstands.platform.EasCommandSender;
import gg.bundlegroup.easyarmorstands.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.session.Session;
import gg.bundlegroup.easyarmorstands.session.SessionListener;
import gg.bundlegroup.easyarmorstands.session.SessionManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.Closeable;

public class Main implements Closeable {
    private final SessionManager sessionManager;

    public Main(EasPlatform platform) {
        this.sessionManager = new SessionManager();
        CommandManager<EasCommandSender> commandManager = platform.commandManager();
        platform.registerListener(new SessionListener(sessionManager));
        platform.registerTickTask(sessionManager::update);
        Command.Builder<EasCommandSender> commandBuilder = commandManager.commandBuilder("eas");
        commandManager.command(
                commandBuilder.literal("give")
                        .permission("easyarmorstands.edit")
                        .senderType(EasPlayer.class)
                        .handler(context -> {
                            EasPlayer player = (EasPlayer) context.getSender();
                            player.giveTool();
                            player.sendMessage(Component.text("Tool added to your inventory", NamedTextColor.GREEN));
                        })
        );

        commandManager.command(
                commandBuilder.literal("visible")
                        .permission("easyarmorstands.edit")
                        .senderType(EasPlayer.class)
                        .handler(context -> {
                            EasPlayer player = (EasPlayer) context.getSender();
                            Session session = sessionManager.getSession(player);
                            if (session != null) {
                                boolean visible = !session.getEntity().isVisible();
                                session.getEntity().setVisible(visible);
                                if (visible) {
                                    sendSuccess(player, "Armor stand set to visible");
                                } else {
                                    sendSuccess(player, "Armor stand set to invisible");
                                }
                            } else {
                                sendNoSessionError(player);
                            }
                        })
        );

        commandManager.command(
                commandBuilder.literal("baseplate")
                        .permission("easyarmorstands.edit")
                        .senderType(EasPlayer.class)
                        .handler(context -> {
                            EasPlayer player = (EasPlayer) context.getSender();
                            Session session = sessionManager.getSession(player);
                            if (session != null) {
                                boolean basePlate = !session.getEntity().hasBasePlate();
                                session.getEntity().setBasePlate(basePlate);
                                if (basePlate) {
                                    sendSuccess(player, "Base plate enabled");
                                } else {
                                    sendSuccess(player, "Base plate disabled");
                                }
                            } else {
                                sendNoSessionError(player);
                            }
                        })
        );

        commandManager.command(
                commandBuilder.literal("arms")
                        .permission("easyarmorstands.edit")
                        .senderType(EasPlayer.class)
                        .handler(context -> {
                            EasPlayer player = (EasPlayer) context.getSender();
                            Session session = sessionManager.getSession(player);
                            if (session != null) {
                                boolean arms = !session.getEntity().hasArms();
                                session.getEntity().setArms(arms);
                                if (arms) {
                                    sendSuccess(player, "Arms enabled");
                                } else {
                                    sendSuccess(player, "Arms disabled");
                                }
                            } else {
                                sendNoSessionError(player);
                            }
                        })
        );

        commandManager.command(
                commandBuilder.literal("gravity")
                        .permission("easyarmorstands.edit")
                        .senderType(EasPlayer.class)
                        .handler(context -> {
                            EasPlayer player = (EasPlayer) context.getSender();
                            Session session = sessionManager.getSession(player);
                            if (session != null) {
                                boolean gravity = !session.getEntity().hasGravity();
                                session.getEntity().setGravity(gravity);
                                if (session.getSkeleton() != null) {
                                    session.getSkeleton().setGravity(gravity);
                                }
                                if (gravity) {
                                    sendSuccess(player, "Gravity enabled");
                                } else {
                                    sendSuccess(player, "Gravity disabled");
                                }
                            } else {
                                sendNoSessionError(player);
                            }
                        })
        );

        commandManager.command(
                commandBuilder.literal("small")
                        .permission("easyarmorstands.edit")
                        .senderType(EasPlayer.class)
                        .handler(context -> {
                            EasPlayer player = (EasPlayer) context.getSender();
                            Session session = sessionManager.getSession(player);
                            if (session != null) {
                                boolean small = !session.getEntity().isSmall();
                                session.getEntity().setSmall(small);
                                if (session.getSkeleton() != null) {
                                    session.getSkeleton().setSmall(small);
                                }
                                if (small) {
                                    sendSuccess(player, "Size changed to small");
                                } else {
                                    sendSuccess(player, "Size changed to normal");
                                }
                            } else {
                                sendNoSessionError(player);
                            }
                        })
        );
    }

    private void sendSuccess(EasCommandSender sender, String message) {
        sender.sendMessage(Component.text(message, NamedTextColor.GREEN));
    }

    private void sendNoSessionError(EasCommandSender sender) {
        sender.sendMessage(Component.text("Not editing an armor stand, right click one using /eas give", NamedTextColor.RED));
    }

    @Override
    public void close() {
        sessionManager.stopAllSessions();
    }
}
