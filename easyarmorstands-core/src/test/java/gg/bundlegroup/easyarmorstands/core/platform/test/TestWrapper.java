package gg.bundlegroup.easyarmorstands.core.platform.test;

import gg.bundlegroup.easyarmorstands.core.platform.EasWrapper;

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
