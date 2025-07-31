package me.m56738.easyarmorstands.huskclaims;

import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.libraries.cloplib.operation.OperationType;
import net.william278.huskclaims.libraries.cloplib.operation.OperationTypeRegistry;
import org.bukkit.event.HandlerList;

import java.lang.reflect.Method;

public class HuskClaimsAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;
    private Class<?> keyClass;
    private Object operationTypeKey;
    private OperationTypeRegistry operationTypeRegistry;
    private OperationType operationType;
    private HuskClaimsPrivilegeChecker privilegeChecker;

    public HuskClaimsAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "HuskClaims";
    }

    @Override
    public void enable() {
        BukkitHuskClaimsAPI api = BukkitHuskClaimsAPI.getInstance();
        operationTypeRegistry = api.getOperationTypeRegistry();

        try {
            registerOperationType();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        privilegeChecker = new HuskClaimsPrivilegeChecker(api, operationType);
        plugin.getServer().getPluginManager().registerEvents(privilegeChecker, plugin);
    }

    @Override
    public void disable() {
        if (privilegeChecker != null) {
            HandlerList.unregisterAll(privilegeChecker);
            privilegeChecker = null;
        }

        if (operationType != null) {
            try {
                unregisterOperationType();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
            operationType = null;
        }
    }

    private void registerOperationType() throws ReflectiveOperationException {
        String adventure = String.join(".", "net", "kyori", "adventure");
        keyClass = Class.forName(adventure + ".key.Key");
        Method keyMethod = keyClass.getMethod("key", String.class, String.class);
        operationTypeKey = keyMethod.invoke(null, "easyarmorstands", "edit");

        Method createOperationTypeMethod = operationTypeRegistry.getClass()
                .getMethod("createOperationType", keyClass, boolean.class);
        operationType = (OperationType) createOperationTypeMethod.invoke(operationTypeRegistry,
                operationTypeKey, true);
        operationTypeRegistry.registerOperationType(operationType);
    }

    private void unregisterOperationType() throws ReflectiveOperationException {
        Method unregisterOperationTypeMethod = operationTypeRegistry.getClass()
                .getMethod("unregisterOperationType", keyClass);
        unregisterOperationTypeMethod.invoke(operationTypeRegistry, operationTypeKey);
        operationType = null;
    }

    @Override
    public void reload() {
    }
}
