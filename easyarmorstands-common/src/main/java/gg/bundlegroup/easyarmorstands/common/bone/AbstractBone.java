package gg.bundlegroup.easyarmorstands.common.bone;

import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.tool.Tool;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBone implements Bone {
    protected final Session session;
    protected final Vector3d position = new Vector3d();
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
            Component component = tool.update();
            tool.show();
            if (component != null) {
                session.getPlayer().sendActionBar(component);
            }
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
        }
    }

    @Override
    public void onRightClick() {
        if (active) {
            active = false;
        } else if (tool != null) {
            Vector3dc target = tool.getLookTarget();
            if (target != null) {
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
        return tool.component();
    }

    @Override
    public Component subtitle() {
        return Component.empty();
    }
}
