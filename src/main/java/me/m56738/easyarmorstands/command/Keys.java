package me.m56738.easyarmorstands.command;

import cloud.commandframework.keys.CloudKey;
import cloud.commandframework.keys.SimpleCloudKey;
import cloud.commandframework.meta.CommandMeta;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;

import java.util.function.Predicate;

public class Keys {
    public static final CloudKey<Session> SESSION = SimpleCloudKey.of("session", TypeToken.get(Session.class));
    public static final CloudKey<Entity> ENTITY = SimpleCloudKey.of("entity", TypeToken.get(Entity.class));

    public static final CommandMeta.Key<Boolean> SESSION_REQUIRED = CommandMeta.Key.of(Boolean.class, "session-required");
    public static final CommandMeta.Key<Predicate<Entity>> ENTITY_REQUIRED = CommandMeta.Key.of(
            new TypeToken<Predicate<Entity>>() {
            }, "entity-required");
}
