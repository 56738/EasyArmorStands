package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.tool.Tool;
import me.m56738.easyarmorstands.util.Util;
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
    private final Vector3d position = new Vector3d();
    private final Map<String, Tool> tools = new HashMap<>();
    private Tool tool;
    private boolean active;

    public AbstractBone(Session session) {
        this.session = session;
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
    public @NotNull Session getSession() {
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
            session.sendActionBar(actionBar);
        } else {
            Tool bestTool = null;
            double bestDistance = Double.POSITIVE_INFINITY;
            int bestPriority = Integer.MIN_VALUE;
            for (Tool tool : tools.values()) {
                tool.refresh();
                tool.showHandles();
                int priority = tool.getPriority();
                if (priority < bestPriority) {
                    continue;
                }
                Vector3dc target = tool.getLookTarget();
                if (target != null) {
                    double distance = target.distanceSquared(Util.toVector3d(session.getPlayer().getEyeLocation()));
                    if (distance < bestDistance || priority > bestPriority) {
                        bestTool = tool;
                        bestDistance = distance;
                        bestPriority = priority;
                    }
                }
            }
            this.tool = bestTool;
            session.showTitle(Title.title(bestTool != null ? bestTool.getName() : Component.empty(), this.getName(),
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
                session.clearTitle();
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
            session.sendActionBar(Component.empty());
            active = false;
            return true;
        }
        return false;
    }

    @Override
    public void select(Tool tool, Vector3dc cursor) {
        session.clearTitle();
        this.active = true;
        this.tool = tool;
        tool.refresh();
        tool.start(cursor);
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
