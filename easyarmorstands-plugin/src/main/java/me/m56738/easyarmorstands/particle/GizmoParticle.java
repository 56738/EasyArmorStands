package me.m56738.easyarmorstands.particle;

import me.m56738.gizmo.api.Gizmo;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class GizmoParticle implements EditorParticle {
    private final Gizmo gizmo;
    private final Set<Player> players = new HashSet<>();

    public GizmoParticle(Gizmo gizmo) {
        this.gizmo = gizmo;
    }

    @Override
    public void show(@NotNull Player player) {
        show();
        players.add(player);
    }

    @Override
    public void show() {
        gizmo.show();
    }

    @Override
    public void update() {
        gizmo.update();
    }

    @Override
    public void hide() {
        players.clear();
        gizmo.hide();
    }

    @Override
    public void hide(@NotNull Player player) {
        players.remove(player);
        if (players.isEmpty()) {
            hide();
        }
    }
}
