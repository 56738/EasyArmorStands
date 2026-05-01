package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.editor.input.InputProvider;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.editor.input.OpenElementMenuInput;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.editor.input.SelectLayerInput;
import me.m56738.easyarmorstands.editor.input.selection.SelectElementInput;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

@NullMarked
public class InputProviderImpl implements InputProvider {
    private final Session session;

    public InputProviderImpl(Session session) {
        this.session = session;
    }

    @Override
    public Input returnInput() {
        return new ReturnInput(session);
    }

    @Override
    public Input openElementMenuInput(MenuElement element) {
        return new OpenElementMenuInput(session, element);
    }

    @Override
    public Input selectElementInput(SelectableElement element, Supplier<Layer> layerSupplier) {
        return new SelectElementInput(session, element, layerSupplier::get);
    }

    @Override
    public Input selectLayerInput(Supplier<Layer> layerSupplier) {
        return new SelectLayerInput(session, layerSupplier::get);
    }
}
