package me.m56738.easyarmorstands.adapter;

import me.m56738.easyarmorstands.session.SessionListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class EntityPlaceAdapter {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void enable(Plugin plugin, SessionListener listener) throws ReflectiveOperationException {
        Class eventClass = Class.forName("org.bukkit.event.entity.EntityPlaceEvent");
        MethodHandle playerGetter = MethodHandles.lookup().findVirtual(eventClass, "getPlayer", MethodType.methodType(Player.class));
        MethodHandle entityGetter = MethodHandles.lookup().findVirtual(eventClass, "getEntity", MethodType.methodType(Entity.class));
        plugin.getServer().getPluginManager().registerEvent(
                eventClass,
                listener,
                EventPriority.NORMAL,
                new Executor(listener, playerGetter, entityGetter),
                plugin);
    }

    private static class Executor implements EventExecutor {
        private final SessionListener sessionListener;
        private final MethodHandle playerGetter;
        private final MethodHandle entityGetter;

        private Executor(SessionListener sessionListener, MethodHandle playerGetter, MethodHandle entityGetter) {
            this.sessionListener = sessionListener;
            this.playerGetter = playerGetter;
            this.entityGetter = entityGetter;
        }

        @Override
        public void execute(Listener listener, Event event) throws EventException {
            Player player;
            Entity entity;
            try {
                player = (Player) playerGetter.invoke(event);
                entity = (Entity) entityGetter.invoke(event);
            } catch (Throwable e) {
                throw new EventException(e);
            }
            sessionListener.onPlaceEntity(player, entity);
        }
    }
}
