package me.m56738.easyarmorstands.command.util;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.minecraft.extras.annotation.specifier.Decoder;

import java.util.function.Function;

public class MiniMessageProvider implements Decoder.Provider {
    @Override
    public Function<String, ? extends Component> decoder(TypeToken<?> parsedType) {
        return EasyArmorStandsCommon.miniMessage()::deserialize;
    }
}
