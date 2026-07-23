package me.m56738.easyarmorstands.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogInput;
import me.m56738.easyarmorstands.platform.dialog.DialogInputProvider;
import me.m56738.easyarmorstands.platform.dialog.DialogResponseView;

import java.util.Collection;
import java.util.Locale;

@SuppressWarnings("UnstableApiUsage")
public interface DialogEntry {
    default void populateBody(Collection<DialogBodyEntry> body) {
    }

    default void populateInputs(Collection<DialogInput> inputs, Locale locale, DialogInputProvider provider) {
    }

    default void save(DialogResponseView response) {
    }

    default void commit() {
    }
}
