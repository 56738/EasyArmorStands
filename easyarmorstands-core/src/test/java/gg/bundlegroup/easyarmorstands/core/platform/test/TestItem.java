package gg.bundlegroup.easyarmorstands.core.platform.test;

import gg.bundlegroup.easyarmorstands.core.platform.EasItem;

public class TestItem extends TestWrapper implements EasItem {
    private final boolean tool;

    public TestItem(TestPlatform platform, boolean tool) {
        super(platform);
        this.tool = tool;
    }

    @Override
    public boolean isTool() {
        return tool;
    }
}
