package me.m56738.easyarmorstands.command.annotation;

import org.bukkit.entity.Entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireEntity {
    Class<? extends Entity> type() default Entity.class;
}
