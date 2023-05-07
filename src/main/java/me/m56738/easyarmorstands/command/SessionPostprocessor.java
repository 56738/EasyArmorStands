package me.m56738.easyarmorstands.command;

import cloud.commandframework.execution.postprocessor.CommandPostprocessingContext;
import cloud.commandframework.execution.postprocessor.CommandPostprocessor;
import cloud.commandframework.services.types.ConsumerService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SessionPostprocessor implements CommandPostprocessor<EasCommandSender> {
    @Override
    public void accept(@NonNull CommandPostprocessingContext<EasCommandSender> ctx) {
        if (ctx.getCommand().getCommandMeta().getOrDefault(Keys.SESSION_REQUIRED, false)) {
            if (!ctx.getCommandContext().contains(Keys.SESSION)) {
                EasCommandSender sender = ctx.getCommandContext().getSender();

                TextComponent.Builder builder = Component.text()
                        .content("You are not using the editor.")
                        .color(NamedTextColor.RED);

                if (sender.get().hasPermission("easyarmorstands.give")) {
                    builder.appendNewline()
                            .append(Component.text("Use "))
                            .append(Component.text()
                                    .content("/eas give")
                                    .decorate(TextDecoration.UNDERLINED)
                                    .clickEvent(ClickEvent.runCommand("/eas give")))
                            .append(Component.text(" to obtain the tool."));
                }

                sender.sendMessage(builder);
                ConsumerService.interrupt();
            }
        }
    }
}
