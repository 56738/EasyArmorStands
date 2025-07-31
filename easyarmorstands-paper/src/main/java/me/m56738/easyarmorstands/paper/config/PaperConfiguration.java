package me.m56738.easyarmorstands.paper.config;

import me.m56738.easyarmorstands.common.config.Configuration;

public class PaperConfiguration implements Configuration {
    @Override
    public double getEditorButtonRange() {
        return 64.0;
    }

    @Override
    public double getEditorButtonThreshold() {
        return 0.1;
    }

    @Override
    public double getEditorScaleMinDistance() {
        return 5.0;
    }

    @Override
    public double getEditorScaleMaxDistance() {
        return 64.0;
    }

    @Override
    public double getGroupBoxSize() {
        return 128.0;
    }

    @Override
    public double getMinEntityScale() {
        return 0.0625;
    }

    @Override
    public double getMaxEntityScale() {
        return 16.0;
    }

    @Override
    public double getMinDisplayEntityScale() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public double getMaxDisplayEntityScale() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public int getSelectionButtonLimit() {
        return 128;
    }

    @Override
    public int getGroupSizeLimit() {
        return 64;
    }
}
