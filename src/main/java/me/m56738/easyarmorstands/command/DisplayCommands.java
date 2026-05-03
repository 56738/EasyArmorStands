package me.m56738.easyarmorstands.command;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.command.annotation.PropertyPermission;
import me.m56738.easyarmorstands.command.requirement.RequireElement;
import me.m56738.easyarmorstands.command.requirement.RequireElementSelection;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.command.util.ElementSelection;
import me.m56738.easyarmorstands.editor.display.layer.DisplayBoxLayer;
import me.m56738.easyarmorstands.editor.display.layer.DisplayLayer;
import me.m56738.easyarmorstands.editor.display.layer.DisplayShearLayer;
import me.m56738.easyarmorstands.element.DisplayElement;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Display.Brightness;
import org.incendo.cloud.annotation.specifier.Greedy;
import org.incendo.cloud.annotation.specifier.Range;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.minecraft.extras.annotation.specifier.Decoder;

import java.util.Optional;

@Command("eas")
public class DisplayCommands {
    @Command("block <value>")
    @PropertyPermission("easyarmorstands:block_display/block")
    @CommandDescription("easyarmorstands.command.description.block")
    @RequireElementSelection
    public void setBlock(EasPlayer sender, ElementSelection selection, @Argument("value") BlockData value) {
        PropertyContainer properties = selection.properties(sender);
        Property<BlockData> property = properties.getOrNull(BlockDisplayPropertyTypes.BLOCK);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.block-unsupported"));
            return;
        }
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-block",
                    property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("brightness block <value>")
    @PropertyPermission("easyarmorstands:display/brightness")
    @CommandDescription("easyarmorstands.command.description.brightness.block")
    @RequireElementSelection
    public void setBlockBrightness(EasPlayer sender, ElementSelection selection, @Argument("value") @Range(min = "0", max = "15") int blockLight) {
        PropertyContainer properties = selection.properties(sender);
        Location location = properties.get(EntityPropertyTypes.LOCATION).getValue();
        Property<Optional<Brightness>> property = properties.getOrNull(DisplayPropertyTypes.BRIGHTNESS);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.brightness-unsupported"));
            return;
        }
        int skyLight = property.getValue()
                .map(Brightness::getSkyLight)
                .orElseGet(() -> (int) location.getBlock().getLightFromSky());
        Optional<Brightness> brightness = Optional.of(new Brightness(blockLight, skyLight));
        if (property.setValue(brightness)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-brightness",
                    property.getType().getValueComponent(brightness)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("brightness sky <value>")
    @PropertyPermission("easyarmorstands:display/brightness")
    @CommandDescription("easyarmorstands.command.description.brightness.sky")
    @RequireElementSelection
    public void setSkyBrightness(EasPlayer sender, ElementSelection selection, @Argument("value") @Range(min = "0", max = "15") int skyLight) {
        PropertyContainer properties = selection.properties(sender);
        Location location = properties.get(EntityPropertyTypes.LOCATION).getValue();
        Property<Optional<Brightness>> property = properties.getOrNull(DisplayPropertyTypes.BRIGHTNESS);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.brightness-unsupported"));
            return;
        }
        int blockLight = property.getValue()
                .map(Brightness::getSkyLight)
                .orElseGet(() -> (int) location.getBlock().getLightFromBlocks());
        Optional<Brightness> brightness = Optional.of(new Brightness(blockLight, skyLight));
        if (property.setValue(brightness)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-brightness",
                    property.getType().getValueComponent(brightness)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("brightness here")
    @PropertyPermission("easyarmorstands:display/brightness")
    @CommandDescription("easyarmorstands.command.description.brightness.here")
    @RequireElementSelection
    public void setLocalBrightness(EasPlayer sender, ElementSelection selection) {
        PropertyContainer properties = selection.properties(sender);
        Property<Optional<Brightness>> property = properties.getOrNull(DisplayPropertyTypes.BRIGHTNESS);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.brightness-unsupported"));
            return;
        }
        Block block = sender.get().getLocation().getBlock();
        Optional<Brightness> brightness = Optional.of(new Brightness(block.getLightFromBlocks(), block.getLightFromSky()));
        if (property.setValue(brightness)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-brightness",
                    property.getType().getValueComponent(brightness)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("brightness reset")
    @PropertyPermission("easyarmorstands:display/brightness")
    @CommandDescription("easyarmorstands.command.description.brightness.reset")
    @RequireElementSelection
    public void setDefaultBrightness(EasPlayer sender, ElementSelection selection) {
        PropertyContainer properties = selection.properties(sender);
        Property<Optional<Brightness>> property = properties.getOrNull(DisplayPropertyTypes.BRIGHTNESS);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.brightness-unsupported"));
            return;
        }
        if (property.setValue(Optional.empty())) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-brightness",
                    property.getType().getValueComponent(Optional.empty())));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("box width <width>")
    @PropertyPermission("easyarmorstands:display/box/width")
    @CommandDescription("easyarmorstands.command.description.box.width")
    @RequireElementSelection
    public void setBoxWidth(EasPlayer sender, ElementSelection selection, @Argument("width") float value) {
        PropertyContainer properties = selection.properties(sender);
        Property<Float> widthProperty = properties.getOrNull(DisplayPropertyTypes.BOX_WIDTH);
        if (widthProperty == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.box-unsupported"));
            return;
        }
        if (widthProperty.setValue(value)) {
            Property<Float> heightProperty = properties.getOrNull(DisplayPropertyTypes.BOX_HEIGHT);
            if (heightProperty != null && heightProperty.getValue() == 0f) {
                heightProperty.setValue(value);
            }
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-box-width",
                    widthProperty.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("box height <height>")
    @PropertyPermission("easyarmorstands:display/box/height")
    @CommandDescription("easyarmorstands.command.description.box.height")
    @RequireElementSelection
    public void setBoxHeight(EasPlayer sender, ElementSelection selection, @Argument("height") float value) {
        PropertyContainer properties = selection.properties(sender);
        Property<Float> heightProperty = properties.getOrNull(DisplayPropertyTypes.BOX_HEIGHT);
        if (heightProperty == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.box-unsupported"));
            return;
        }
        if (heightProperty.setValue(value)) {
            Property<Float> widthProperty = properties.getOrNull(DisplayPropertyTypes.BOX_WIDTH);
            if (widthProperty != null && widthProperty.getValue() == 0f) {
                widthProperty.setValue(value);
            }
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-box-height",
                    heightProperty.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("box remove")
    @PropertyPermission("easyarmorstands:display/box/height")
    @CommandDescription("easyarmorstands.command.description.box.remove")
    @RequireElementSelection
    public void removeBox(EasPlayer sender, ElementSelection selection) {
        PropertyContainer properties = selection.properties(sender);

        int success = 0;
        Property<Float> widthProperty = properties.getOrNull(DisplayPropertyTypes.BOX_WIDTH);
        Property<Float> heightProperty = properties.getOrNull(DisplayPropertyTypes.BOX_HEIGHT);
        if (widthProperty == null && heightProperty == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.box-unsupported"));
            return;
        }

        if (widthProperty != null && widthProperty.setValue(0f)) {
            success++;
        }
        if (heightProperty != null && heightProperty.setValue(0f)) {
            success++;
        }

        if (success > 0) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.removed-box"));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("box")
    @PropertyPermission("easyarmorstands:display/translation")
    @CommandDescription("easyarmorstands.command.description.box")
    @RequireElement
    public void moveBox(EasPlayer sender, Session session, Element element) {
        if (!(element instanceof DisplayElement<?>)) {
            sender.sendMessage(Message.error("easyarmorstands.error.box-unsupported"));
            return;
        }
        PropertyContainer properties = new TrackedPropertyContainer(element, sender);
        session.pushLayer(new DisplayBoxLayer(session, properties));
    }

    @Command("text")
    @PropertyPermission("easyarmorstands:text_display/text")
    @CommandDescription("easyarmorstands.command.description.text")
    @RequireElement
    public void showText(EasPlayer sender, Element element) {
        Property<Component> property = element.getProperties().getOrNull(TextDisplayPropertyTypes.TEXT);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.text-unsupported"));
            return;
        }
        Component text = property.getValue();
        SessionCommands.showText(sender, TextDisplayPropertyTypes.TEXT.getName(), text, "/eas text set");
    }

    @Command("text set <value>")
    @PropertyPermission("easyarmorstands:text_display/text")
    @CommandDescription("easyarmorstands.command.description.text.set")
    @RequireElementSelection
    public void setText(EasPlayer sender, ElementSelection selection, @Argument("value") @Decoder.MiniMessage @Greedy Component value) {
        PropertyContainer properties = selection.properties(sender);
        Property<Component> property = properties.getOrNull(TextDisplayPropertyTypes.TEXT);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.text-unsupported"));
            return;
        }
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-text",
                    property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("text width <value>")
    @PropertyPermission("easyarmorstands:text_display/line_width")
    @CommandDescription("easyarmorstands.command.description.text.width")
    @RequireElementSelection
    public void setTextWidth(EasPlayer sender, ElementSelection selection, @Argument("value") int value) {
        PropertyContainer properties = selection.properties(sender);
        Property<Integer> property = properties.getOrNull(TextDisplayPropertyTypes.LINE_WIDTH);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.text-line-width-unsupported"));
            return;
        }
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-text-line-width",
                    property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("text background color <value>")
    @PropertyPermission("easyarmorstands:text_display/background")
    @CommandDescription("easyarmorstands.command.description.text.background.color")
    @RequireElementSelection
    public void setTextBackground(EasPlayer sender, ElementSelection selection, @Argument("value") TextColor color) {
        PropertyContainer properties = selection.properties(sender);
        Property<Optional<Color>> property = properties.getOrNull(TextDisplayPropertyTypes.BACKGROUND);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.text-background-unsupported"));
            return;
        }
        Optional<Color> value = Optional.of(Color.fromRGB(color.value()));
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-text-background",
                    property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("text background reset")
    @PropertyPermission("easyarmorstands:text_display/background")
    @CommandDescription("easyarmorstands.command.description.text.background.reset")
    @RequireElementSelection
    public void resetTextBackground(EasPlayer sender, ElementSelection selection) {
        PropertyContainer properties = selection.properties(sender);
        Property<Optional<Color>> property = properties.getOrNull(TextDisplayPropertyTypes.BACKGROUND);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.text-background-unsupported"));
            return;
        }
        if (property.setValue(Optional.empty())) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-text-background",
                    property.getType().getValueComponent(Optional.empty())));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("text background none")
    @PropertyPermission("easyarmorstands:text_display/background")
    @CommandDescription("easyarmorstands.command.description.text.background.none")
    @RequireElementSelection
    public void hideTextBackground(EasPlayer sender, ElementSelection selection) {
        PropertyContainer properties = selection.properties(sender);
        Property<Optional<Color>> property = properties.getOrNull(TextDisplayPropertyTypes.BACKGROUND);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.text-background-unsupported"));
            return;
        }
        Optional<Color> value = Optional.of(Color.fromARGB(0));
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-text-background",
                    property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("text background alpha <value>")
    @PropertyPermission("easyarmorstands:text_display/background")
    @CommandDescription("easyarmorstands.command.description.text.background.alpha")
    @RequireElementSelection
    public void hideTextBackground(EasPlayer sender, ElementSelection selection, @Argument("value") @Range(min = "0", max = "255") int alpha) {
        PropertyContainer properties = selection.properties(sender);
        Property<Optional<Color>> property = properties.getOrNull(TextDisplayPropertyTypes.BACKGROUND);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.text-background-unsupported"));
            return;
        }
        Optional<Color> oldValue = property.getValue();
        if (!oldValue.isPresent()) {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
            return;
        }

        Optional<Color> value = oldValue.map(v -> v.setAlpha(alpha));
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-text-background-alpha",
                    Component.text(alpha)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("glow color <value>")
    @PropertyPermission("easyarmorstands:display/glowing/color")
    @CommandDescription("easyarmorstands.command.description.glow.color")
    @RequireElementSelection
    public void setGlowColor(EasPlayer sender, ElementSelection selection, @Argument("value") TextColor color) {
        PropertyContainer properties = selection.properties(sender);
        Property<Optional<Color>> property = properties.getOrNull(DisplayPropertyTypes.GLOW_COLOR);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.glow-color-unsupported"));
            return;
        }
        Optional<Color> value = Optional.of(Color.fromRGB(color.value()));
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-glow-color",
                    property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("glow reset")
    @PropertyPermission("easyarmorstands:display/glowing/color")
    @CommandDescription("easyarmorstands.command.description.glow.color.reset")
    @RequireElementSelection
    public void resetGlowColor(EasPlayer sender, ElementSelection selection) {
        PropertyContainer properties = selection.properties(sender);
        Property<Optional<Color>> property = properties.getOrNull(DisplayPropertyTypes.GLOW_COLOR);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.glow-color-unsupported"));
            return;
        }
        Optional<Color> value = Optional.empty();
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-glow-color",
                    property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }

    @Command("shear")
    @PropertyPermission("easyarmorstands:display/right_rotation")
    @CommandDescription("easyarmorstands.command.description.shear")
    @RequireElement
    public void editRightRotation(EasPlayer sender, Session session, Element element) {
        if (!(element instanceof DisplayElement<?>)) {
            sender.sendMessage(Message.error("easyarmorstands.error.shearing-unsupported"));
            return;
        }
        PropertyContainer properties = new TrackedPropertyContainer(element, sender);
        DisplayLayer layer = new DisplayShearLayer(session, properties, (DisplayElement<?>) element);
        session.pushLayer(layer);
    }

    @Command("viewrange <value>")
    @PropertyPermission("easyarmorstands:display/view_range")
    @CommandDescription("easyarmorstands.command.description.view-range")
    @RequireElementSelection
    public void setViewRange(EasPlayer sender, ElementSelection selection, @Argument("value") float value) {
        PropertyContainer properties = selection.properties(sender);
        Property<Float> property = properties.getOrNull(DisplayPropertyTypes.VIEW_RANGE);
        if (property == null) {
            sender.sendMessage(Message.error("easyarmorstands.error.view-range-unsupported"));
            return;
        }
        if (property.setValue(value)) {
            properties.commit();
            sender.sendMessage(Message.success("easyarmorstands.success.changed-view-range",
                    property.getType().getValueComponent(value)));
        } else {
            sender.sendMessage(Message.error("easyarmorstands.error.cannot-change"));
        }
    }
}
