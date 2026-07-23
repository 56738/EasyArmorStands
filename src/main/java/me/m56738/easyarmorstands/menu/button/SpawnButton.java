package me.m56738.easyarmorstands.menu.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.layer.ElementSelectionLayer;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementSpawnRequest;
import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.platform.entity.Player;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.List;

public class SpawnButton implements MenuButton {
    private final EasyArmorStandsCommon eas;
    private final Player player;
    private final ElementType type;
    private final MenuIcon icon;

    public SpawnButton(EasyArmorStandsCommon eas, Player player, ElementType type, MenuIcon icon) {
        this.eas = eas;
        this.player = player;
        this.type = type;
        this.icon = icon;
    }

    @Override
    public Key key() {
        return type.key();
    }

    @Override
    public MenuIcon icon() {
        return icon;
    }

    @Override
    public Component name() {
        return type.getDisplayName();
    }

    @Override
    public List<Component> description() {
        return List.of();
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (context.isLeftClick()) {
            ElementSpawnRequest spawnRequest = eas.elementSpawnRequest(type);
            spawnRequest.setPlayer(player);
            Element element = spawnRequest.spawn();

            Session session = eas.sessionManager().getSession(player);
            if (session != null) {
                ElementSelectionLayer selectionLayer = session.findLayer(ElementSelectionLayer.class);
                if (selectionLayer != null) {
                    if (element instanceof SelectableElement) {
                        selectionLayer.selectElement((SelectableElement) element);
                    }
                }
            }

            context.closeMenu();
        }
    }
}
