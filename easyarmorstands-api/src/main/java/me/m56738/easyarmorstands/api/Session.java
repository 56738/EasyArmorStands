package me.m56738.easyarmorstands.api;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public interface Session {
    Player player();

    ArmorStand entity();

    boolean running();

    void start();

    void stop();
}
