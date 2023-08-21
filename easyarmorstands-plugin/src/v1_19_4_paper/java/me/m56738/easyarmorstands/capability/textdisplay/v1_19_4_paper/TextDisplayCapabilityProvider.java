package me.m56738.easyarmorstands.capability.textdisplay.v1_19_4_paper;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.component.v1_16_paper.NativeComponentMapper;
import me.m56738.easyarmorstands.capability.textdisplay.TextDisplayCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.Plugin;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class TextDisplayCapabilityProvider implements CapabilityProvider<TextDisplayCapability> {
    @Override
    public boolean isSupported() {
        NativeComponentMapper mapper = NativeComponentMapper.getInstance();
        if (mapper == null) {
            return false;
        }
        try {
            TextDisplay.class.getMethod("text");
            TextDisplay.class.getMethod("text", mapper.getComponentClass());
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public TextDisplayCapability create(Plugin plugin) {
        try {
            NativeComponentMapper mapper = NativeComponentMapper.getInstance();
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle getText = lookup.findVirtual(TextDisplay.class, "text",
                    MethodType.methodType(mapper.getComponentClass()));
            MethodHandle setText = lookup.findVirtual(TextDisplay.class, "text",
                    MethodType.methodType(void.class, mapper.getComponentClass()));
            return new TextDisplayCapabilityImpl(mapper, getText, setText);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TextDisplayCapabilityImpl implements TextDisplayCapability {
        private final NativeComponentMapper mapper;
        private final MethodHandle getText;
        private final MethodHandle setText;

        private TextDisplayCapabilityImpl(NativeComponentMapper mapper, MethodHandle getText, MethodHandle setText) {
            this.mapper = mapper;
            this.getText = getText;
            this.setText = setText;
        }

        @Override
        public Component getText(TextDisplay entity) {
            try {
                return mapper.convertFromNative(getText.invoke(entity));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void setText(TextDisplay entity, Component text) {
            try {
                setText.invoke(entity, mapper.convertToNative(text));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
