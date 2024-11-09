package me.m56738.easyarmorstands.config;

import me.m56738.easyarmorstands.config.version.IntegrationConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class EasConfig {
    public UpdateCheckConfig updateCheck = new UpdateCheckConfig();
    public EditorConfig editor = new EditorConfig();
    public MessageConfig message = new MessageConfig();
    public IntegrationConfig integration = new IntegrationConfig();
}
