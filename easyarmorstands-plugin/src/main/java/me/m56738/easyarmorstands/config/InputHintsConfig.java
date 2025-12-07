package me.m56738.easyarmorstands.config;


import net.kyori.adventure.text.Component;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public class InputHintsConfig {
    public boolean enabled;
    public String format;
    public Component rightClickKey;
    public Component leftClickKey;
    public Component swapHandsKey;
    public Component sneakKey;
}
