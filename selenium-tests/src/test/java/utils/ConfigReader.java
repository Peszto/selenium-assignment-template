package utils;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found on classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing config key: " + key);
        }
        return value;
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public static Duration getImplicitWait() {
        return Duration.ofSeconds(Long.parseLong(get("implicit.wait")));
    }

    public static Duration getExplicitWait() {
        return Duration.ofSeconds(Long.parseLong(get("explicit.wait")));
    }

    public static Duration getShortWait() {
        return Duration.ofSeconds(Long.parseLong(get("short.wait")));
    }

}
