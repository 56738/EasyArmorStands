package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuContext;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import net.kyori.adventure.permission.PermissionChecker;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class ElementMenuContext implements MenuContext {
    private final ChangeContext context;
    private final Session session;
    private final Element element;
    private final PropertyContainer properties;
    private final PermissionChecker permissions;
    private final Locale locale;
    private final TagResolver resolver;

    public ElementMenuContext(ChangeContext context, Session session, Element element) {
        this.context = context;
        this.session = session;
        this.element = element;
        this.properties = new TrackedPropertyContainer(element, context);
        this.permissions = context.permissions();
        this.locale = context.locale();
        this.resolver = TagResolver.builder()
                .tag("type", Tag.selfClosingInserting(element.getType().getDisplayName()))
                .build();
    }

    @Override
    public @Nullable Session session() {
        return session;
    }

    @Override
    public @Nullable Element element() {
        return element;
    }

    @Override
    public @Nullable PropertyContainer properties() {
        return properties;
    }

    @Override
    public @NotNull PropertyContainer properties(Element element) {
        return new TrackedPropertyContainer(element, context);
    }

    @Override
    public @NotNull PermissionChecker permissions() {
        return permissions;
    }

    @Override
    public @NotNull Locale locale() {
        return locale;
    }

    @Override
    public @NotNull TagResolver resolver() {
        return resolver;
    }

    @Override
    public @Nullable ColorPickerContext colorPicker() {
        return null;
    }
}
