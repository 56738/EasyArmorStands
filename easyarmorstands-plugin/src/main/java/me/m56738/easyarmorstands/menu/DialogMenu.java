package me.m56738.easyarmorstands.menu;

import io.papermc.paper.dialog.Dialog;
import me.m56738.easyarmorstands.api.menu.Menu;
import org.bukkit.entity.Player;

public class DialogMenu implements Menu {
    private final Dialog dialog;

    public DialogMenu(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void open(Player player) {
        player.showDialog(dialog);
    }
}
