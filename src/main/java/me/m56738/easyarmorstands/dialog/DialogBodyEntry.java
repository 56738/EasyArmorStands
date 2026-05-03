package me.m56738.easyarmorstands.dialog;

import io.papermc.paper.registry.data.dialog.body.DialogBody;

import java.util.List;
import java.util.Locale;

@SuppressWarnings("UnstableApiUsage")
public interface DialogBodyEntry {
    void populateBody(List<DialogBody> body, Locale locale);
}
