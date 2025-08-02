package me.m56738.easyarmorstands.neoforge.session;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.modded.session.ModdedSessionListener;
import me.m56738.easyarmorstands.neoforge.EasyArmorStandsMod;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class NeoForgeSessionListener extends ModdedSessionListener {
    public NeoForgeSessionListener(EasyArmorStandsCommonProvider easProvider) {
        super(easProvider);
    }

    public void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
        register();
    }

    @Override
    public void register() {
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.EntityInteractSpecific.class,
                e -> apply(e, handleClick(e.getEntity(), e.getLevel(), ClickContext.Type.RIGHT_CLICK, e.getTarget(), null)));
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.EntityInteract.class,
                e -> apply(e, handleClick(e.getEntity(), e.getLevel(), ClickContext.Type.RIGHT_CLICK, e.getTarget(), null)));
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.RightClickBlock.class,
                e -> apply(e, handleClick(e.getEntity(), e.getLevel(), ClickContext.Type.RIGHT_CLICK, null, e.getPos())));
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.RightClickItem.class,
                e -> apply(e, handleClick(e.getEntity(), e.getLevel(), ClickContext.Type.RIGHT_CLICK, null, null)));
        NeoForge.EVENT_BUS.addListener(AttackEntityEvent.class,
                e -> apply(e, handleClick(e.getEntity(), e.getEntity().level(), ClickContext.Type.LEFT_CLICK, e.getTarget(), null)));
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.LeftClickBlock.class,
                e -> apply(e, handleClick(e.getEntity(), e.getEntity().level(), ClickContext.Type.LEFT_CLICK, null, e.getPos())));
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.LeftClickEmpty.class,
                e -> handleClick(e.getEntity(), e.getEntity().level(), ClickContext.Type.LEFT_CLICK, null, null));
        NeoForge.EVENT_BUS.addListener(PlayerLoggedInEvent.class, e -> handleUpdateItem(e.getEntity()));
        NeoForge.EVENT_BUS.addListener(ServerTickEvent.Post.class, e -> {
            for (ServerPlayer player : e.getServer().getPlayerList().getPlayers()) {
                int slot = player.getInventory().getSelectedSlot();
                LastSelectedSlot lastSlot = player.getData(LAST_SELECTED_SLOT);
                if (slot != lastSlot.slot) {
                    lastSlot.slot = slot;
                    handleUpdateItem(player);
                }
                if (player.swinging && player.swingTime == 0) {
                    handleClick(player, player.level(), ClickContext.Type.LEFT_CLICK, null, null);
                }
            }
        });
    }

    private void apply(ICancellableEvent event, boolean consumed) {
        if (consumed) {
            event.setCanceled(true);
        }
    }

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, EasyArmorStandsMod.MODID);
    private static final Supplier<AttachmentType<LastSelectedSlot>> LAST_SELECTED_SLOT = ATTACHMENT_TYPES.register("last_selected_slot", () -> AttachmentType.builder(LastSelectedSlot::new).build());

    private static class LastSelectedSlot {
        private int slot = -1;
    }
}
