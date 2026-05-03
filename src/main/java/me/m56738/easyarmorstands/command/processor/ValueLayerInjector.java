package me.m56738.easyarmorstands.command.processor;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.editor.layer.ValueLayer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.injection.ParameterInjector;
import org.incendo.cloud.util.annotation.AnnotationAccessor;

@SuppressWarnings("rawtypes")
public class ValueLayerInjector implements ParameterInjector<EasCommandSender, ValueLayer> {
    @Override
    public @Nullable ValueLayer create(@NonNull CommandContext<EasCommandSender> context, @NonNull AnnotationAccessor annotationAccessor) {
        EasCommandSender sender = context.sender();
        if (!(sender instanceof EasPlayer)) {
            return null;
        }
        EasPlayer player = (EasPlayer) sender;
        Session session = player.session();
        if (session == null) {
            return null;
        }
        Layer layer = session.getLayer();
        if (!(layer instanceof ValueLayer)) {
            return null;
        }
        ValueLayer valueLayer = (ValueLayer) layer;
        if (!valueLayer.canSetValue()) {
            return null;
        }
        return valueLayer;
    }
}
