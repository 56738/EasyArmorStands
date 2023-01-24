package gg.bundlegroup.easyarmorstands.core.platform.test;

import cloud.commandframework.Command;
import cloud.commandframework.internal.CommandRegistrationHandler;
import org.checkerframework.checker.nullness.qual.NonNull;

public class TestCommandRegistrationHandler implements CommandRegistrationHandler {
    @Override
    public boolean registerCommand(@NonNull Command<?> command) {
        return true;
    }
}
