package me.m56738.easyarmorstands.huskclaims;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.Addon;
import net.kyori.adventure.key.Key;
import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.libraries.cloplib.operation.OperationType;
import net.william278.huskclaims.libraries.cloplib.operation.OperationTypeRegistry;
import org.bukkit.plugin.Plugin;

public class HuskClaimsAddon implements Addon {
    private static final Key EDIT = EasyArmorStands.key("edit");
    private final Plugin plugin;
    private final EasyArmorStandsPaperImpl eas;
    private OperationTypeRegistry operationTypeRegistry;
    private OperationType operationType;
    private HuskClaimsPrivilegeChecker privilegeChecker;

    public HuskClaimsAddon(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        this.plugin = plugin;
        this.eas = eas;
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
        eas.regionPrivilegeManager().registerPrivilegeChecker(plugin, privilegeChecker);
    }

    @Override
    public void disable() {
        if (privilegeChecker != null) {
            eas.regionPrivilegeManager().unregisterPrivilegeChecker(privilegeChecker);
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
        operationType = operationTypeRegistry.createOperationType(EDIT, true);
        operationTypeRegistry.registerOperationType(operationType);
    }

    private void unregisterOperationType() throws ReflectiveOperationException {
        operationTypeRegistry.unregisterOperationType(EDIT);
        operationType = null;
    }

    @Override
    public void reload() {
    }
}
