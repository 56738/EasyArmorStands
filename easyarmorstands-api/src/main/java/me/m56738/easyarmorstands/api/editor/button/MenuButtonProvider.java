package me.m56738.easyarmorstands.api.editor.button;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface MenuButtonProvider {
    MoveButtonBuilder move();

    ScaleButtonBuilder scale();

    RotateButtonBuilder rotate();

    CarryButtonBuilder carry();
}
