package me.m56738.easyarmorstands.platform.event;

import net.kyori.adventure.key.Key;

record EventTypeImpl<C>(Key key, EventDispatcher<C> dispatcher) implements EventType<C> {
}
