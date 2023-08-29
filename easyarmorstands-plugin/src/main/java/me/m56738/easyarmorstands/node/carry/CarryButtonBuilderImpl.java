package me.m56738.easyarmorstands.node.carry;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.CarryAxis;
import me.m56738.easyarmorstands.api.editor.node.menu.CarryButton;
import me.m56738.easyarmorstands.api.editor.node.menu.CarryButtonBuilder;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class CarryButtonBuilderImpl implements CarryButtonBuilder {
    private final Session session;
    private CarryAxis carryAxis;
    private Component name = Message.component("easyarmorstands.node.pick-up");
    private ParticleColor color = ParticleColor.WHITE;
    private int priority;

    public CarryButtonBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull CarryButtonBuilder setAxis(@NotNull CarryAxis axis) {
        this.carryAxis = axis;
        return this;
    }

    @Override
    public @NotNull CarryButtonBuilder setName(@NotNull Component name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull CarryButtonBuilder setColor(@NotNull ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public @NotNull CarryButtonBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public @NotNull CarryButton build() {
        CarryButtonImpl button = new CarryButtonImpl(session, carryAxis, color, name);
        button.setPriority(priority);
        return button;
    }
}
