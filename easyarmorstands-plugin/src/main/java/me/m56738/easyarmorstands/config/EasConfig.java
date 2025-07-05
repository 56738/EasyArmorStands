package me.m56738.easyarmorstands.config;

import me.m56738.easyarmorstands.config.integration.IntegrationConfig;
import me.m56738.easyarmorstands.lib.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class EasConfig {
    public UpdateCheckConfig updateCheck = new UpdateCheckConfig();
    public EditorConfig editor = new EditorConfig();
    public MessageConfig message = new MessageConfig();
    public IntegrationConfig integration = new IntegrationConfig();
    public LimitConfig limits = new LimitConfig();
}
