package me.m56738.easyarmorstands.huskclaims;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import net.kyori.adventure.key.Key;
import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.libraries.cloplib.operation.OperationType;
import net.william278.huskclaims.libraries.cloplib.operation.OperationTypeRegistry;

public class HuskClaimsAddon implements Addon {
    private static final Key EDIT = EasyArmorStands.key("edit");
    private OperationTypeRegistry operationTypeRegistry;
    private OperationType operationType;
    private HuskClaimsPrivilegeChecker privilegeChecker;

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
        EasyArmorStands.get().regionPrivilegeManager().registerPrivilegeChecker(EasyArmorStandsPlugin.getInstance(), privilegeChecker);
    }

    @Override
    public void disable() {
        if (privilegeChecker != null) {
            EasyArmorStands.get().regionPrivilegeManager().unregisterPrivilegeChecker(privilegeChecker);
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
