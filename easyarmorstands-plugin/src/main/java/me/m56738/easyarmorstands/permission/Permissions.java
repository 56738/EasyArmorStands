package me.m56738.easyarmorstands.permission;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.permissions.Permission;
import org.intellij.lang.annotations.MagicConstant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Permissions {
    @Description("Allow aligning entities to the block grid")
    public static final String ALIGN = "easyarmorstands.align";

    @Description("Allow copying properties")
    public static final String CLIPBOARD = "easyarmorstands.clipboard";

    @Description("Allow cloning entities")
    public static final String CLONE = "easyarmorstands.clone";

    @Description("Allow using the color picker")
    public static final String COLOR = "easyarmorstands.color";

    @Description("Allow converting armor stands to item displays")
    public static final String CONVERT = "easyarmorstands.convert";

    @Description("Allow copying entities as items (armor stands or spawn eggs)")
    public static final String COPY_ENTITY = "easyarmorstands.copy.entity";

    @Description("Allow viewing troubleshooting information")
    public static final String DEBUG = "easyarmorstands.debug";

    @Description("Allow destroying entities")
    public static final String DESTROY = "easyarmorstands.destroy";

    @Description("Allow spawning the tool item")
    public static final String GIVE = "easyarmorstands.give";

    @Description("Allow bypassing GriefDefender restrictions")
    public static final String GRIEFDEFENDER_BYPASS = "easyarmorstands.griefdefender.bypass";

    @Description("Allow editing groups of entities")
    public static final String GROUP = "easyarmorstands.group";

    @Description("Allow using the help command")
    public static final String HELP = "easyarmorstands.help";

    @Description("Allow showing the history")
    public static final String HISTORY = "easyarmorstands.history";

    @Description("Allow viewing details of the selected entity")
    public static final String INFO = "easyarmorstands.info";

    @Description("Allow bypassing Lands restrictions")
    public static final String LANDS_BYPASS = "easyarmorstands.lands.bypass";

    @Description("Allow opening the menu")
    public static final String OPEN = "easyarmorstands.open";

    @Description("Allow bypassing PlotSquared restrictions")
    public static final String PLOTSQUARED_BYPASS = "easyarmorstands.plotsquared.bypass";

    @Description("Allow redoing changes")
    public static final String REDO = "easyarmorstands.redo";

    @Description("Allow reloading the configuration")
    public static final String RELOAD = "easyarmorstands.reload";

    @Description("Allow bypassing Residence restrictions")
    public static final String RESIDENCE_BYPASS = "easyarmorstands.residence.bypass";

    @Description("Allow configuring the snapping increment")
    public static final String SNAP = "easyarmorstands.snap";

    @Description("Allow selecting matching entities using a selector")
    public static final String SELECT = "easyarmorstands.select";

    @Description("Allow selecting entities with a certain tag")
    public static final String SELECT_TAG = "easyarmorstands.select.tag";

    @Description("Allow spawning entities")
    public static final String SPAWN = "easyarmorstands.spawn";

    @Description("Allow using the TrainCarts resource pack model browser")
    public static final String TRAINCARTS_MODEL = "easyarmorstands.traincarts.model";

    @Description("Allow undoing changes")
    public static final String UNDO = "easyarmorstands.undo";

    @Description("Be notified when a new version of EasyArmorStands is available")
    public static final String UPDATE_NOTIFY = "easyarmorstands.update.notify";

    @Description("Allow viewing the plugin version")
    public static final String VERSION = "easyarmorstands.version";

    @Description("Allow bypassing WorldGuard restrictions")
    public static final String WORLDGUARD_BYPASS = "easyarmorstands.worldguard.bypass";

    @Description("Allow using the editor")
    @Child(GROUP)
    @Child(HELP)
    @Child(HISTORY)
    @Child(INFO)
    @Child(OPEN)
    @Child(VERSION)
    public static final String EDIT = "easyarmorstands.edit";

    // ---
    private static final Map<String, Permission> registeredPermissions = new HashMap<>();

    private Permissions() {
    }

    public static void registerAll() {
        try {
            for (Field field : Permissions.class.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                    register(field);
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        for (EntityType entityType : EntityType.values()) {
            register(entityType, SPAWN, "spawning");
            register(entityType, DESTROY, "destroying");
            register(entityType, EDIT, "editing");
        }
    }

    public static String entityType(
            @MagicConstant(valuesFromClass = Permissions.class) String prefix,
            EntityType type) {
        return prefix + "." + type.name().toLowerCase(Locale.ROOT).replace("_", "");
    }

    private static void register(EntityType type, String owner, String verb) {
        String name = entityType(owner, type);
        Map<String, Boolean> children = new HashMap<>(1);
        children.put(owner, true);
        register(new Permission(
                name,
                "Allow " + verb + " entities of type " + type,
                children));
    }

    public static Permission register(Permission permission) {
        try {
            Bukkit.getPluginManager().addPermission(permission);
        } catch (IllegalArgumentException e) {
            return null;
        }
        registeredPermissions.put(permission.getName(), permission);
        return permission;
    }

    public static void unregisterAll() {
        for (Permission permission : registeredPermissions.values()) {
            Bukkit.getPluginManager().removePermission(permission);
        }
        registeredPermissions.clear();
    }

    public static void unregister(Permission permission) {
        registeredPermissions.remove(permission.getName(), permission);
        Bukkit.getPluginManager().removePermission(permission);
    }

    private static void register(Field field) throws ReflectiveOperationException {
        String name = (String) field.get(null);

        Description descriptionAnnotation = field.getDeclaredAnnotation(Description.class);
        String description = descriptionAnnotation != null ? descriptionAnnotation.value() : null;

        Children childrenAnnotation = field.getDeclaredAnnotation(Children.class);
        Map<String, Boolean> children = new HashMap<>();
        if (childrenAnnotation != null) {
            for (Child child : childrenAnnotation.value()) {
                children.put(child.value(), true);
            }
        }

        register(new Permission(name, description, children));
    }
}
