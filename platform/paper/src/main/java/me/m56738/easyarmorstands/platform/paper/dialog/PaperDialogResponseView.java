package me.m56738.easyarmorstands.platform.paper.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogResponseView;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public interface PaperDialogResponseView extends DialogResponseView {
    static PaperDialogResponseView fromNative(io.papermc.paper.dialog.DialogResponseView view) {
        return new PaperDialogResponseViewImpl(view);
    }

    io.papermc.paper.dialog.DialogResponseView getNative();

    static io.papermc.paper.dialog.DialogResponseView toNative(DialogResponseView view) {
        return ((PaperDialogResponseView) view).getNative();
    }

    @Override
    default @Nullable Boolean getBoolean(String key) {
        return getNative().getBoolean(key);
    }

    @Override
    default @Nullable String getText(String key) {
        return getNative().getText(key);
    }
}
