package me.m56738.easyarmorstands;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class EasyArmorStandsHolder {
    private @Nullable EasyArmorStandsCommon eas;

    public void initialize(@Nullable EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    public EasyArmorStandsCommon get() {
        if (eas == null) {
            throw new IllegalStateException();
        }
        return eas;
    }
}
