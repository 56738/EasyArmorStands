package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

@ApiStatus.NonExtendable
public interface ElementSelectionNode extends Node {
    @Contract(pure = true)
    double getRange();

    void setRange(double range);

    void addSource(@NotNull ElementDiscoverySource source);

    void removeSource(@NotNull ElementDiscoverySource source);

    @Unmodifiable
    @NotNull
    Iterable<ElementDiscoverySource> getSources();

    boolean selectElement(@NotNull SelectableElement element);

    boolean selectElements(@NotNull List<? extends SelectableElement> elements);
}
