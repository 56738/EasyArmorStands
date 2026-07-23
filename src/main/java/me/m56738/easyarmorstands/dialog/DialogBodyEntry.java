package me.m56738.easyarmorstands.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogBody;
import me.m56738.easyarmorstands.platform.dialog.DialogBodyProvider;

import java.util.List;
import java.util.Locale;

@SuppressWarnings("UnstableApiUsage")
public interface DialogBodyEntry {
    void populateBody(List<DialogBody> body, Locale locale, DialogBodyProvider provider);
}
