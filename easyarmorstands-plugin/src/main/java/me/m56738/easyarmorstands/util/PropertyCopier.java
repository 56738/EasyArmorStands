package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;

public class PropertyCopier {
    private int successCount;
    private int failureCount;

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void copyProperties(PropertyContainer destination, PropertyContainer source) {
        source.forEach(p -> {
            if (copyProperty(destination, p)) {
                successCount++;
            } else {
                failureCount++;
            }
        });
    }

    private <T> boolean copyProperty(PropertyContainer destination, Property<T> source) {
        Property<T> property = destination.getOrNull(source.getType());
        if (property != null) {
            return property.setValue(source.getValue());
        }
        return false;
    }
}
