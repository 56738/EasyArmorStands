package me.m56738.easyarmorstands.api.platform.entity;

public interface Player extends Entity, CommandSender {
    boolean isSneaking();

    boolean isValid();
}
