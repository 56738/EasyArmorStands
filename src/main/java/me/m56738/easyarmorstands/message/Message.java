package me.m56738.easyarmorstands.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Message {
    ERROR_NOT_USING_EDITOR("error.not-using-editor"),
    ERROR_NOT_USING_EDITOR_HINT("error.not-using-editor-hint"),
    OVERVIEW("overview"),
    TOOL_GIVEN("tool-given"),
    COMMAND_GIVE("command.give"),
    ;
    private final String path;

    Message(String path) {
        this.path = path;
    }

    private String getTemplate() {
        if (MessageLoader.getConfig().isList(path)) {
            return String.join("<br>", MessageLoader.getConfig().getStringList(path));
        }
        return MessageLoader.getConfig().getString(path);
    }

    private List<String> getTemplateList() {
        if (!MessageLoader.getConfig().isList(path)) {
            return Collections.singletonList(MessageLoader.getConfig().getString(path));
        }
        return MessageLoader.getConfig().getStringList(path);
    }

    public Component render() {
        return MessageLoader.getSerializer().deserialize(getTemplate());
    }

    public Component render(TagResolver resolver) {
        return MessageLoader.getSerializer().deserialize(getTemplate(), resolver);
    }

    public Component render(TagResolver... resolvers) {
        return MessageLoader.getSerializer().deserialize(getTemplate(), resolvers);
    }

    public List<Component> renderList() {
        List<String> templateList = getTemplateList();
        List<Component> result = new ArrayList<>(templateList.size());
        MiniMessage serializer = MessageLoader.getSerializer();
        for (String template : templateList) {
            result.add(serializer.deserialize(template));
        }
        return result;
    }

    public List<Component> renderList(TagResolver resolver) {
        List<String> templateList = getTemplateList();
        List<Component> result = new ArrayList<>(templateList.size());
        MiniMessage serializer = MessageLoader.getSerializer();
        for (String template : templateList) {
            result.add(serializer.deserialize(template, resolver));
        }
        return result;
    }

    public List<Component> renderList(TagResolver... resolvers) {
        List<String> templateList = getTemplateList();
        List<Component> result = new ArrayList<>(templateList.size());
        MiniMessage serializer = MessageLoader.getSerializer();
        for (String template : templateList) {
            result.add(serializer.deserialize(template, resolvers));
        }
        return result;
    }
}
