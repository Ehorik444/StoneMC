package space.bxteam.divinemc.configuration;

import org.apache.commons.lang.BooleanUtils;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static space.bxteam.divinemc.configuration.DivineConfig.log;

@SuppressWarnings("unused")
public class DivineWorldConfig {
    private final String worldName;
    private final World.Environment environment;

    public DivineWorldConfig(String worldName, World.Environment environment) {
        this.worldName = worldName;
        this.environment = environment;
        init();
    }

    public void init() {
        log("-------- World Settings For [" + worldName + "] --------");
        DivineConfig.readConfig(DivineWorldConfig.class, this);
    }

    private void set(String path, Object val) {
        DivineConfig.config.addDefault("world-settings.default." + path, val);
        DivineConfig.config.set("world-settings.default." + path, val);
        if (DivineConfig.config.get("world-settings." + worldName + "." + path) != null) {
            DivineConfig.config.addDefault("world-settings." + worldName + "." + path, val);
            DivineConfig.config.set("world-settings." + worldName + "." + path, val);
        }
    }

    private ConfigurationSection getConfigurationSection(String path) {
        ConfigurationSection section = DivineConfig.config.getConfigurationSection("world-settings." + worldName + "." + path);
        return section != null ? section : DivineConfig.config.getConfigurationSection("world-settings.default." + path);
    }

    private String getString(String path, String def) {
        DivineConfig.config.addDefault("world-settings.default." + path, def);
        return DivineConfig.config.getString("world-settings." + worldName + "." + path, DivineConfig.config.getString("world-settings.default." + path));
    }

    private boolean getBoolean(String path, boolean def) {
        DivineConfig.config.addDefault("world-settings.default." + path, def);
        return DivineConfig.config.getBoolean("world-settings." + worldName + "." + path, DivineConfig.config.getBoolean("world-settings.default." + path));
    }

    private boolean getBoolean(String path, Predicate<Boolean> predicate) {
        String val = getString(path, "default").toLowerCase();
        Boolean bool = BooleanUtils.toBooleanObject(val, "true", "false", "default");
        return predicate.test(bool);
    }

    private double getDouble(String path, double def) {
        DivineConfig.config.addDefault("world-settings.default." + path, def);
        return DivineConfig.config.getDouble("world-settings." + worldName + "." + path, DivineConfig.config.getDouble("world-settings.default." + path));
    }

    private int getInt(String path, int def) {
        DivineConfig.config.addDefault("world-settings.default." + path, def);
        return DivineConfig.config.getInt("world-settings." + worldName + "." + path, DivineConfig.config.getInt("world-settings.default." + path));
    }

    private <T> List<?> getList(String path, T def) {
        DivineConfig.config.addDefault("world-settings.default." + path, def);
        return DivineConfig.config.getList("world-settings." + worldName + "." + path, DivineConfig.config.getList("world-settings.default." + path));
    }

    private Map<String, Object> getMap(String path, Map<String, Object> def) {
        final Map<String, Object> fallback = DivineConfig.getMap("world-settings.default." + path, def);
        final Map<String, Object> value = DivineConfig.getMap("world-settings." + worldName + "." + path, null);
        return value.isEmpty() ? fallback : value;
    }

    public boolean saveFireworks = false;
    private void saveFireworks() {
        saveFireworks = getBoolean("gameplay-mechanics.should-save-fireworks", saveFireworks);
    }
}
