package me.m56738.easyarmorstands.core.inventory;

import me.m56738.easyarmorstands.core.bone.Bone;
import me.m56738.easyarmorstands.core.platform.EasItem;
import me.m56738.easyarmorstands.core.platform.EasMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;

public class SelectBoneSlot implements InventorySlot {
    private final SessionMenu menu;
    private final Bone bone;
    private final EasMaterial type;
    private final Component name;

    public SelectBoneSlot(SessionMenu menu, Bone bone, EasMaterial type, Component name) {
        this.menu = menu;
        this.bone = bone;
        this.type = type;
        this.name = name;
    }

    @Override
    public void initialize(int slot) {
        EasItem item = menu.getInventory().platform().createItem(
                type,
                Component.text()
                        .content("Edit ")
                        .append(name)
                        .color(NamedTextColor.BLUE)
                        .build(),
                Arrays.asList(
                        Component.text("Selects this bone", NamedTextColor.GRAY),
                        Component.text("in the editor.", NamedTextColor.GRAY)
                )
        );
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, EasItem cursor) {
        if (click) {
            menu.getSession().setBone(bone);
            menu.queueTask(() -> menu.getSession().getPlayer().closeInventory(menu.getInventory()));
        }
        return false;
    }
}
