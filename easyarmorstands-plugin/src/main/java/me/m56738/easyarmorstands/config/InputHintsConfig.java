package me.m56738.easyarmorstands.config;

import me.m56738.easyarmorstands.lib.configurate.objectmapping.ConfigSerializable;
import net.kyori.adventure.text.Component;

@ConfigSerializable
public class InputHintsConfig {
    public boolean enabled;
    public String format;
    public Component rightClickKey;
    public Component leftClickKey;
    public Component swapHandsKey;
    public Component sneakKey;
}
