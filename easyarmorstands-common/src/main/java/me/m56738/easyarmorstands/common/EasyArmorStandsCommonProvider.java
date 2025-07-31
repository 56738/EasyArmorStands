package me.m56738.easyarmorstands.common;

import me.m56738.easyarmorstands.api.EasyArmorStandsProvider;

public interface EasyArmorStandsCommonProvider extends EasyArmorStandsProvider {
    boolean hasEasyArmorStands();

    @Override
    EasyArmorStandsCommon getEasyArmorStands();
}
