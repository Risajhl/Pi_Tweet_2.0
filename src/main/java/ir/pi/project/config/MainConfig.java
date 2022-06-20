package ir.pi.project.config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainConfig {
    private String mainConfigPath =
            "src\\main\\resources\\config\\mainConfig";

    private String resourcesPath;
    private String addresses;
    private String texts;
    private String limits;
    private String showChatConfig;

    public MainConfig() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfigPath);
        properties.load(fileReader);
        resourcesPath = (String) properties.get("resourcesPath");
        addresses = (String) properties.get("addresses");
        texts = (String) properties.get("texts");
        limits = (String) properties.get("limits");
        showChatConfig = (String) properties.get("showChatConfig");

    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public String getAddresses() {
        return addresses;
    }

    public String getTexts() {
        return texts;
    }

    public String getLimits() {
        return limits;
    }

    public String getShowChatConfig() {
        return showChatConfig;
    }
}
