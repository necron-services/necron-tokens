package dev.necron.token.common.config.key;

import dev.necron.token.common.config.ConfigType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigKey<V> {

    private final ConfigType configType;
    private final String[] path;
    private V value;

    public ConfigKey(V value, ConfigType configType, String... path) {
        this.value = value;
        this.configType = configType;
        this.path = path;
    }

}
