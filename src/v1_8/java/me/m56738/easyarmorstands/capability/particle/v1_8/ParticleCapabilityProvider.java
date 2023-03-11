package me.m56738.easyarmorstands.capability.particle.v1_8;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ParticleCapabilityProvider implements CapabilityProvider<ParticleCapability> {
    private ParticleCapability instance;

    public ParticleCapabilityProvider() {
        try {
            instance = new ParticleCapabilityImpl();
        } catch (Throwable ignored) {
        }
    }

    @Override
    public boolean isSupported() {
        return instance != null;
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    public ParticleCapability create(Plugin plugin) {
        return instance;
    }

    private static class ParticleCapabilityImpl implements ParticleCapability {
        private final Object particleType;
        private final MethodHandle packetConstructor;
        private final MethodHandle handleGetter;
        private final MethodHandle connectionGetter;
        private final MethodHandle packetSender;

        public ParticleCapabilityImpl() throws Throwable {
            Class<?> particleTypeClass = Class.forName("net.minecraft.server.v1_8_R3.EnumParticle");
            particleType = particleTypeClass.getDeclaredField("REDSTONE").get(null);

            Class<?> particlePacketClass = Class.forName("net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles");
            packetConstructor = MethodHandles.publicLookup().findConstructor(particlePacketClass, MethodType.methodType(
                    void.class,
                    particleTypeClass,
                    boolean.class,
                    float.class, float.class, float.class,
                    float.class, float.class, float.class,
                    float.class,
                    int.class,
                    int[].class
            ));

            Class<?> playerClass = Class.forName("org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer");
            Class<?> handleClass = Class.forName("net.minecraft.server.v1_8_R3.EntityPlayer");
            handleGetter = MethodHandles.publicLookup().findVirtual(playerClass, "getHandle", MethodType.methodType(handleClass));

            Class<?> connectionClass = Class.forName("net.minecraft.server.v1_8_R3.PlayerConnection");
            connectionGetter = MethodHandles.publicLookup().findGetter(handleClass, "playerConnection", connectionClass);

            Class<?> packetClass = Class.forName("net.minecraft.server.v1_8_R3.Packet");
            packetSender = MethodHandles.publicLookup().findVirtual(connectionClass, "sendPacket", MethodType.methodType(void.class, packetClass));
        }

        @Override
        public void spawnParticle(Player player, double x, double y, double z, Color color) {
            try {
                Object packet = packetConstructor.invoke(
                        particleType,
                        true,
                        (float) x, (float) y, (float) z,
                        Math.max(color.getRed() / 255f, Float.MIN_VALUE),
                        color.getGreen() / 255f,
                        color.getBlue() / 255f,
                        1f,
                        0
                );
                Object handle = handleGetter.invoke(player);
                Object connection = connectionGetter.invoke(handle);
                packetSender.invoke(connection, packet);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public double getDensity() {
            return 3;
        }
    }
}
