package me.m56738.easyarmorstands.common.util;

public final class ReflectionUtil {
    private ReflectionUtil() {
    }

    public static boolean hasClass(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
