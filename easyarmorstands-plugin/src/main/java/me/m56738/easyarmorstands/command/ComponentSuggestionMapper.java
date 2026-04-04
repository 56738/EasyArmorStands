package me.m56738.easyarmorstands.command;

import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import org.incendo.cloud.brigadier.suggestion.TooltipSuggestion;
import org.incendo.cloud.minecraft.extras.suggestion.ComponentTooltipSuggestion;
import org.incendo.cloud.suggestion.Suggestion;
import org.incendo.cloud.suggestion.SuggestionMapper;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ComponentSuggestionMapper implements SuggestionMapper<Suggestion> {
    @Override
    public Suggestion map(Suggestion suggestion) {
        if (suggestion instanceof ComponentTooltipSuggestion tooltipSuggestion) {
            return TooltipSuggestion.suggestion(
                    tooltipSuggestion.suggestion(),
                    MessageComponentSerializer.message().serializeOrNull(tooltipSuggestion.tooltip()));
        } else {
            return suggestion;
        }
    }
}
