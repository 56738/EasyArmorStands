package me.m56738.easyarmorstands.command.sender;

import me.m56738.easyarmorstands.EasyArmorStandsHolder;
import me.m56738.easyarmorstands.platform.command.CommandSender;
import me.m56738.easyarmorstands.platform.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.SenderMapper;

public class CommandSenderMapper implements SenderMapper<CommandSender, EasCommandSender> {
    private final EasyArmorStandsHolder eas;

    public CommandSenderMapper(EasyArmorStandsHolder eas) {
        this.eas = eas;
    }

    @Override
    public @NonNull EasCommandSender map(@NonNull CommandSender base) {
        if (base instanceof Player player && eas.isInitialized()) {
            return new EasPlayer(eas.get(), player);
        }
        return new EasCommandSender(base);
    }

    @Override
    public @NonNull CommandSender reverse(@NonNull EasCommandSender mapped) {
        return mapped.get();
    }
}
