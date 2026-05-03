package me.m56738.easyarmorstands.dialog;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.input.DialogInput;

import java.util.Collection;
import java.util.Locale;

@SuppressWarnings("UnstableApiUsage")
public interface DialogEntry {
    default void populateBody(Collection<DialogBodyEntry> body) {
    }

    default void populateInputs(Collection<DialogInput> inputs, Locale locale) {
    }

    default void save(DialogResponseView response) {
    }

    default void commit() {
    }
}
