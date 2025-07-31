package me.m56738.easyarmorstands.common.command.annotation;

import net.kyori.adventure.key.KeyPattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Deprecated(forRemoval = true)
public @interface PropertyPermission {
    @KeyPattern
    String value();
}
