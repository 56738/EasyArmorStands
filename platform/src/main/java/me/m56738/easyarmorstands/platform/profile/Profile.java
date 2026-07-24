package me.m56738.easyarmorstands.platform.profile;

import net.kyori.adventure.text.object.ObjectContentsLike;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;

public interface Profile extends ObjectContentsLike {
    @Override
    PlayerHeadObjectContents asObjectContents();
}
