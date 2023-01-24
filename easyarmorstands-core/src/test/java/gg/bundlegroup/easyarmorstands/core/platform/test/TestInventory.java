package gg.bundlegroup.easyarmorstands.core.platform.test;

import gg.bundlegroup.easyarmorstands.core.platform.EasInventory;
import gg.bundlegroup.easyarmorstands.core.platform.EasItem;

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
