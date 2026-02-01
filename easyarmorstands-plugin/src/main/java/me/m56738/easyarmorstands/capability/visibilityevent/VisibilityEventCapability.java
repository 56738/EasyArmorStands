package me.m56738.easyarmorstands.capability.visibilityevent;

import me.m56738.easyarmorstands.capability.Capability;

@Capability(name = "Entity visibility events", optional = true)
public interface VisibilityEventCapability {
    void addListener(VisibilityEventListener listener);

    void removeListener(VisibilityEventListener listener);
}
