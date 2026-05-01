package me.m56738.easyarmorstands.api.editor.input;

import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

@NullMarked
@ApiStatus.NonExtendable
public interface InputProvider {
    Input returnInput();

    Input openElementMenuInput(MenuElement element);

    Input selectElementInput(SelectableElement element, Supplier<Layer> layerSupplier);

    Input selectLayerInput(Supplier<Layer> layerSupplier);
}
