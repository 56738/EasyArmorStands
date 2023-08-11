package me.m56738.easyarmorstands.command.processor;

import cloud.commandframework.annotations.AnnotationAccessor;
import cloud.commandframework.annotations.injection.ParameterInjector;
import cloud.commandframework.context.CommandContext;
import me.m56738.easyarmorstands.editor.EditableObject;
import me.m56738.easyarmorstands.node.EditableObjectNode;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.session.Session;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PropertyContainerInjector<C> implements ParameterInjector<C, PropertyContainer> {
    @Override
    public @Nullable PropertyContainer create(@NonNull CommandContext<C> context, @NonNull AnnotationAccessor annotationAccessor) {
        Session session = SessionPreprocessor.getSessionOrNull(context);
        if (session != null) {
            EditableObjectNode node = session.findNode(EditableObjectNode.class);
            if (node != null) {
                EditableObject editableObject = node.getEditableObject();
                if (editableObject != null) {
                    return session.properties(editableObject);
                }
            }
        }
        return PropertyContainer.empty();
    }
}
