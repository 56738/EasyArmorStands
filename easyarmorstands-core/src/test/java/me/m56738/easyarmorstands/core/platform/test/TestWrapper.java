package me.m56738.easyarmorstands.core.platform.test;

import me.m56738.easyarmorstands.core.platform.EasWrapper;

public class TestWrapper implements EasWrapper {
    private final TestPlatform platform;

    public TestWrapper(TestPlatform platform) {
        this.platform = platform;
    }

    @Override
    public TestPlatform platform() {
        return platform;
    }
}
