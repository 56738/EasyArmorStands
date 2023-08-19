package me.m56738.easyarmorstands.message;

public enum MessageStyle {
    SUCCESS("<green><message>"),
    WARNING("<yellow><message>"),
    ERROR("<red><message>"),
    HINT("<gray><message>"),
    BUTTON("<gray>[<message>]"),
    HOVER("<gray><message>");

    private final String defaultFormat;

    MessageStyle(String defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    public String getDefaultFormat() {
        return defaultFormat;
    }
}
