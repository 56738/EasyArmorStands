package me.m56738.easyarmorstands.editor.display.layer;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.layer.ResettableLayer;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.element.DisplayElement;
import me.m56738.easyarmorstands.element.DisplayShearToolProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class DisplayShearLayer extends DisplayLayer implements ResettableLayer {
    private final Session session;
    private final Component name;
    private final Property<Quaternionfc> rightRotationProperty;

    public DisplayShearLayer(Session session, PropertyContainer properties, DisplayElement<?> element) {
        super(session, properties);
        this.session = session;
        this.name = DisplayPropertyTypes.RIGHT_ROTATION.getName().color(NamedTextColor.GOLD);
        this.rightRotationProperty = properties.get(DisplayPropertyTypes.RIGHT_ROTATION);
        DisplayShearToolProvider tools = element.getShearTools(properties);
        addNode(session.nodeProvider().tools(tools));
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(name);
        context.addInput(new ReturnInput(session));
    }

    @Override
    public void reset() {
        rightRotationProperty.setValue(new Quaternionf());
    }
}
