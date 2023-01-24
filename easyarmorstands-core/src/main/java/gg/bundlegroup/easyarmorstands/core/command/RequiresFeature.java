package gg.bundlegroup.easyarmorstands.core.command;

import cloud.commandframework.meta.CommandMeta;
import gg.bundlegroup.easyarmorstands.core.platform.EasFeature;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresFeature {
    CommandMeta.Key<EasFeature> KEY = CommandMeta.Key.of(EasFeature.class, "requires-feature");

    EasFeature value();
}
