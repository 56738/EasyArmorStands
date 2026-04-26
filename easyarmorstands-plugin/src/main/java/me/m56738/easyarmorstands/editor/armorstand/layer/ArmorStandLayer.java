package me.m56738.easyarmorstands.editor.armorstand.layer;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.editor.input.OpenElementMenuInput;
import me.m56738.easyarmorstands.editor.layer.PropertyMenuLayer;
import me.m56738.easyarmorstands.element.ArmorStandElement;
import me.m56738.easyarmorstands.permission.Permissions;
import org.jetbrains.annotations.NotNull;

public class ArmorStandLayer extends PropertyMenuLayer {
    private final Session session;
    private final ArmorStandElement element;
    private final boolean allowMenu;

    public ArmorStandLayer(Session session, PropertyContainer properties, ArmorStandElement element) {
        super(session, properties);
        this.session = session;
        this.element = element;
        this.allowMenu = session.player().hasPermission(Permissions.OPEN);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        if (allowMenu) {
            context.addInput(new OpenElementMenuInput(session, element));
        }
    }
}
