package me.m56738.easyarmorstands.command.annotation;

import me.m56738.easyarmorstands.lib.kyori.adventure.key.KeyPattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyPermission {
    @KeyPattern
    String value();
}
