package gg.bundlegroup.easyarmorstands.common.platform.test;

import gg.bundlegroup.easyarmorstands.common.platform.EasInventory;
import gg.bundlegroup.easyarmorstands.common.platform.EasItem;

public class TestInventory extends TestWrapper implements EasInventory {
    public TestInventory(TestPlatform platform) {
        super(platform);
    }

    @Override
    public EasItem getItem(int slot) {
        return new TestItem(platform(), false);
    }

    @Override
    public void setItem(int slot, EasItem item) {
    }
}
