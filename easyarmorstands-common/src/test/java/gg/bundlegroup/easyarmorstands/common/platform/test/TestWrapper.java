package gg.bundlegroup.easyarmorstands.common.platform.test;

import gg.bundlegroup.easyarmorstands.common.platform.EasWrapper;

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
