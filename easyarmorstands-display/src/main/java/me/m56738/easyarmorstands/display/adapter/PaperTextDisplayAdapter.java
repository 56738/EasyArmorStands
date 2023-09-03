package me.m56738.easyarmorstands.display.adapter;

import me.m56738.easyarmorstands.util.NativeComponentMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class PaperTextDisplayAdapter extends TextDisplayAdapter {
    private static final PaperTextDisplayAdapter instance = initialize();
    private final NativeComponentMapper mapper;
    private final MethodHandle getText;
    private final MethodHandle setText;

    private PaperTextDisplayAdapter(NativeComponentMapper mapper, MethodHandle getText, MethodHandle setText) {
        this.mapper = mapper;
        this.getText = getText;
        this.setText = setText;
    }

    public static PaperTextDisplayAdapter getInstance() {
        return instance;
    }

    private static PaperTextDisplayAdapter initialize() {
        try {
            NativeComponentMapper mapper = NativeComponentMapper.getInstance();
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle getText = lookup.findVirtual(TextDisplay.class, "text",
                    MethodType.methodType(mapper.getComponentClass()));
            MethodHandle setText = lookup.findVirtual(TextDisplay.class, "text",
                    MethodType.methodType(void.class, mapper.getComponentClass()));
            return new PaperTextDisplayAdapter(mapper, getText, setText);
        } catch (ReflectiveOperationException e) {
            return null;
        }
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
