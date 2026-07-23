package me.m56738.easyarmorstands.editor.input.selection;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.editor.input.SelectLayerInput;
import me.m56738.easyarmorstands.editor.layer.LayerFactory;
import org.jetbrains.annotations.NotNull;

public class SelectElementInput extends SelectLayerInput {
    private final EasyArmorStandsCommon eas;
    private final Session session;
    private final SelectableElement element;

    public SelectElementInput(EasyArmorStandsCommon eas, Session session, SelectableElement element, LayerFactory layerFactory) {
        super(session, layerFactory, null);
        this.eas = eas;
        this.session = session;
        this.element = element;
    }

    @Override
    public void execute(@NotNull ClickContext context) {
        EasPlayer player = new EasPlayer(eas, session.player());
        if (!player.canEditElement(element)) {
            return;
        }
        super.execute(context);
    }
}
