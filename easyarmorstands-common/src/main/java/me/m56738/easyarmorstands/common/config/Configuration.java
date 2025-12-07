package me.m56738.easyarmorstands.common.config;

public interface Configuration {
    double getEditorButtonRange();

    double getEditorButtonThreshold();

    double getEditorScaleMinDistance();

    double getEditorScaleMaxDistance();

    double getGroupBoxSize();

    double getMinEntityScale();

    double getMaxEntityScale();

    default double clampEntityScale(double value) {
        return Math.clamp(getMinEntityScale(), getMaxEntityScale(), value);
    }

    double getMinDisplayEntityScale();

    double getMaxDisplayEntityScale();

    default double clampDisplayEntityScale(double value) {
        return Math.clamp(getMinDisplayEntityScale(), getMaxDisplayEntityScale(), value);
    }

    int getSelectionButtonLimit();

    int getGroupSizeLimit();

    boolean isShowInputHints();
}
