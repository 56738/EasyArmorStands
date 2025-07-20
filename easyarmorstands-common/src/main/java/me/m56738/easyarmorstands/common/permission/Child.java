package me.m56738.easyarmorstands.common.permission;

import org.intellij.lang.annotations.MagicConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Children.class)
public @interface Child {
    @MagicConstant(valuesFromClass = Permissions.class)
    String value();
}
