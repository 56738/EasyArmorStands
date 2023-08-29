package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.editor.node.menu.MoveButton;
import me.m56738.easyarmorstands.api.editor.node.menu.RotateButton;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandPartOffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandRotationProvider;
import me.m56738.easyarmorstands.editor.armorstand.axis.ArmorStandPoseGlobalRotateAxis;
import me.m56738.easyarmorstands.editor.armorstand.axis.ArmorStandPoseLocalRotateAxis;
import me.m56738.easyarmorstands.editor.axis.LocationMoveAxis;
import me.m56738.easyarmorstands.editor.axis.LocationRelativeMoveAxis;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.ArmorStandPartInfo;
import net.kyori.adventure.text.Component;
import org.bukkit.util.EulerAngle;

import java.util.EnumMap;

public class ArmorStandPartNode extends PropertyMenuNode implements ResettableNode {
    private final Session session;
    private final ArmorStandPart part;
    private final Component localName;
    private final Component globalName;
    private final EnumMap<Axis, MoveButton> moveEntries = new EnumMap<>(Axis.class);
    private final EnumMap<Axis, RotateButton> rotateEntries = new EnumMap<>(Axis.class);
    private final ArmorStandPartOffsetProvider offsetProvider;
    private final ArmorStandRotationProvider rotationProvider;
    private boolean local;

    public ArmorStandPartNode(Session session, PropertyContainer container, ArmorStandPart part) {
        super(session, container);
        ArmorStandPartInfo info = ArmorStandPartInfo.of(part);
        this.session = session;
        this.part = part;
        this.localName = Message.component("easyarmorstands.node.local-part", info.getDisplayName());
        this.globalName = Message.component("easyarmorstands.node.global-part", info.getDisplayName());
        this.offsetProvider = new ArmorStandPartOffsetProvider(container, part);
        this.rotationProvider = new ArmorStandRotationProvider(container, part);
        setLocal(true);
    }

    public void setLocal(boolean local) {
        this.local = local;

        for (MoveButton entry : moveEntries.values()) {
            removeButton(entry);
        }
        for (RotateButton entry : rotateEntries.values()) {
            removeButton(entry);
        }

        for (Axis axis : Axis.values()) {
            MoveAxis moveAxis;
            if (local) {
                moveAxis = new LocationRelativeMoveAxis(properties(), axis, offsetProvider, rotationProvider);
            } else {
                moveAxis = new LocationMoveAxis(properties(), axis, offsetProvider);
            }
            moveEntries.put(axis, session.menuEntryProvider()
                    .move()
                    .setAxis(moveAxis)
                    .build());
            rotateEntries.put(axis, session.menuEntryProvider()
                    .rotate()
                    .setAxis(local
                            ? new ArmorStandPoseLocalRotateAxis(properties(), part, axis)
                            : new ArmorStandPoseGlobalRotateAxis(properties(), part, axis))
                    .build());
        }
        for (MoveButton entry : moveEntries.values()) {
            addButton(entry);
        }
        for (RotateButton entry : rotateEntries.values()) {
            addButton(entry);
        }
    }

    @Override
    public void onUpdate(UpdateContext context) {
        super.onUpdate(context);
        session.setActionBar(local ? localName : globalName);
    }

    @Override
    public boolean onClick(ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }
        if (context.type() == ClickContext.Type.LEFT_CLICK) {
            session.popNode();
            return true;
        }
        if (context.type() == ClickContext.Type.RIGHT_CLICK) {
            setLocal(!local);
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        properties().get(ArmorStandPropertyTypes.POSE.get(part)).setValue(EulerAngle.ZERO);
        properties().commit();
    }
}
