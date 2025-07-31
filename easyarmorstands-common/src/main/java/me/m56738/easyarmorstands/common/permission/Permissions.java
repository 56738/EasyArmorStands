package me.m56738.easyarmorstands.common.permission;

import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import org.intellij.lang.annotations.MagicConstant;

import java.util.Locale;

public class Permissions {
    @Description("Allow aligning entities to the block grid")
    public static final String ALIGN = "easyarmorstands.align";

    @Description("Allow bypassing BentoBox restrictions")
    public static final String BENTOBOX_BYPASS = "easyarmorstands.bentobox.bypass";

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

    @Description("Allow destroying FancyHolograms holograms")
    public static final String FANCYHOLOGRAMS_DESTROY = "easyarmorstands.fancyholograms.destroy";

    @Description("Allow editing FancyHolograms holograms")
    public static final String FANCYHOLOGRAMS_EDIT = "easyarmorstands.fancyholograms.edit";

    @Description("Allow spawning FancyHolograms holograms")
    public static final String FANCYHOLOGRAMS_SPAWN = "easyarmorstands.fancyholograms.spawn";

    @Description("Allow spawning the tool item")
    public static final String GIVE = "easyarmorstands.give";

    @Description("Allow bypassing GriefDefender restrictions")
    public static final String GRIEFDEFENDER_BYPASS = "easyarmorstands.griefdefender.bypass";

    @Description("Allow bypassing GriefPrevention restrictions")
    public static final String GRIEFPREVENTION_BYPASS = "easyarmorstands.griefprevention.bypass";

    @Description("Allow editing groups of entities")
    public static final String GROUP = "easyarmorstands.group";

    @Description("Allow using the help command")
    public static final String HELP = "easyarmorstands.help";

    @Description("Allow showing the history")
    public static final String HISTORY = "easyarmorstands.history";

    @Description("Allow bypassing HuskClaims restrictions")
    public static final String HUSKCLAIMS_BYPASS = "easyarmorstands.huskclaims.bypass";

    @Description("Allow viewing details of the selected entity")
    public static final String INFO = "easyarmorstands.info";

    @Description("Allow bypassing Lands restrictions")
    public static final String LANDS_BYPASS = "easyarmorstands.lands.bypass";

    @Description("Allow opening the menu")
    public static final String OPEN = "easyarmorstands.open";

    @Description("Allow teleporting entities")
    public static final String POSITION = "easyarmorstands.position";

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

    @Description("Allow bypassing Towny restrictions")
    public static final String TOWNY_BYPASS = "easyarmorstands.towny.bypass";

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

    public static String entityType(
            @MagicConstant(valuesFromClass = Permissions.class) String prefix,
            EntityType type) {
        return prefix + "." + type.name().toLowerCase(Locale.ROOT).replace("_", "");
    }
}
