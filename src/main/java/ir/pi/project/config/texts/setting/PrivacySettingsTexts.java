package ir.pi.project.config.texts.setting;

import ir.pi.project.config.texts.TextsConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PrivacySettingsTexts {
    TextsConfig textsConfig=new TextsConfig();
    private String wrongPassword;
    private String saved;

    public PrivacySettingsTexts() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(textsConfig.getPrivacySettings());
        properties.load(fileReader);
        wrongPassword = (String) properties.get("wrongPassword");
        saved = (String) properties.get("saved");
    }

    public String getWrongPassword() {
        return wrongPassword;
    }

    public String getSaved() {
        return saved;
    }
}


