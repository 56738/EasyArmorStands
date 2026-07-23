package me.m56738.easyarmorstands.platform.paper.world;

record PaperWorldImpl(org.bukkit.World world) implements PaperWorld {
    @Override
    public org.bukkit.World getNative() {
        return world;
    }
}
