package gg.bundlegroup.easyarmorstands.common.bone;

import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.tool.Tool;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBone implements Bone {
    private final Session session;
    private final EasPlayer player;
    private final Vector3d position = new Vector3d();
    private final Map<String, Tool> tools = new HashMap<>();
    private Tool tool;
    private boolean active;

    public AbstractBone(Session session) {
        this.session = session;
        this.player = session.getPlayer();
    }

    @Override
    public void addTool(String name, Tool tool) {
        tools.put(name, tool);
    }

    @Override
    public Map<String, Tool> getTools() {
        return tools;
    }

    @Override
    public @NotNull Session session() {
        return session;
    }

    @Override
    public void start() {
        this.active = false;
        this.tool = null;
    }

    @Override
    public void update() {
        if (this.active) {
            Component value = tool.update();
            tool.show();
            TextComponent.Builder actionBar = Component.text();
            actionBar.append(tool.getDescription());
            if (value != null) {
                actionBar.append(Component.text(": "));
                actionBar.append(value);
            }
            session.getPlayer().sendActionBar(actionBar);
        } else {
            Tool bestTool = null;
            double bestDistance = Double.POSITIVE_INFINITY;
            for (Tool tool : tools.values()) {
                tool.refresh();
                tool.showHandles();
                Vector3dc target = tool.getLookTarget();
                if (target != null) {
                    double distance = target.distanceSquared(session.getPlayer().getEyePosition());
                    if (distance < bestDistance) {
                        bestTool = tool;
                        bestDistance = distance;
                    }
                }
            }
            this.tool = bestTool;
            player.showTitle(Title.title(bestTool != null ? bestTool.getName() : Component.empty(), this.getName(),
                    Title.Times.times(Duration.ZERO, Ticks.duration(20), Duration.ZERO)));
        }
    }

    @Override
    public void onRightClick() {
        if (active) {
            active = false;
        } else if (tool != null) {
            Vector3dc target = tool.getLookTarget();
            if (target != null) {
                player.clearTitle();
                active = true;
                tool.refresh();
                tool.start(target);
            }
        }
    }

    @Override
    public boolean onLeftClick() {
        if (active) {
            tool.abort();
            session.getPlayer().sendActionBar(Component.empty());
            active = false;
            return true;
        }
        return false;
    }

    @Override
    public void select(Tool tool) {
        player.clearTitle();
        this.active = true;
        this.tool = tool;
        tool.refresh();
        tool.start(tool.getTarget());
    }

    @Override
    public Vector3dc getPosition() {
        return position;
    }

    @Override
    public Component title() {
        if (tool == null) {
            return Component.empty();
        }
        return tool.getName();
    }
}
