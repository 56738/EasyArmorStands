package me.m56738.easyarmorstands.command.util;

import io.leangen.geantyref.TypeToken;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.minecraft.extras.annotation.specifier.Decoder;

import java.util.function.Function;

public class MiniMessageProvider implements Decoder.Provider {
    private final MiniMessageDecoder decoder = new MiniMessageDecoder();

    @Override
    public Function<String, ? extends Component> decoder(TypeToken<?> parsedType) {
        return decoder;
    }
}
