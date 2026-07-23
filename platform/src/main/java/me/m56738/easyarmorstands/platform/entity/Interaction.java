package me.m56738.easyarmorstands.platform.entity;

public interface Interaction extends Entity {
    float getInteractionWidth();

    void setInteractionWidth(float width);

    float getInteractionHeight();

    void setInteractionHeight(float height);

    boolean isResponsive();

    void setResponsive(boolean responsive);
}
