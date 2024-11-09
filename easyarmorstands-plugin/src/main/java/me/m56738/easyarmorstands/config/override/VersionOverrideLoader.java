package me.m56738.easyarmorstands.config.override;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.config.serializer.VersionOverrideConditionSerializer;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNodeFactory;
import org.spongepowered.configurate.ScopedConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class VersionOverrideLoader {
    private final List<VersionOverride> overrides = new ArrayList<>();

    public void load(EasyArmorStandsPlugin plugin) {
        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .defaultOptions(o -> o.serializers(b -> b.register(VersionOverrideCondition.class, new VersionOverrideConditionSerializer())))
                .source(plugin.getConfigSource("version-overrides.yml"))
                .build();

        List<VersionOverride> overrides;
        try {
            overrides = loader.load().getList(VersionOverride.class);
        } catch (ConfigurateException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load version overrides", e);
            return;
        }

        if (overrides == null) {
            return;
        }

        for (VersionOverride override : overrides) {
            if (override.condition.testCondition()) {
                this.overrides.add(override);
            }
        }
    }

    public <N extends ScopedConfigurationNode<N>> N apply(String name, N config, ConfigurationNodeFactory<N> factory) {
        N result = factory.createNode(config.options());
        for (VersionOverride override : overrides) {
            if (override.name.equals(name)) {
                N newResult = factory.createNode(config.options());
                newResult.node(override.path).mergeFrom(override.value);
                newResult.mergeFrom(result);
                result = newResult;
            }
        }
        result.mergeFrom(config);
        return result;
    }
}
