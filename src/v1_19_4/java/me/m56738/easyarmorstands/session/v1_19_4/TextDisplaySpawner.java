package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;

public class TextDisplaySpawner extends DisplaySpawner<TextDisplay> {
    public TextDisplaySpawner(JOMLMapper mapper) {
        super(TextDisplay.class, EntityType.TEXT_DISPLAY, mapper);
    }

    @Override
    protected void configure(TextDisplay entity) {
        entity.setText("Text");
    }
}
