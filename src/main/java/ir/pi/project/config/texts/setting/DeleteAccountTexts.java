package ir.pi.project.config.texts.setting;

import ir.pi.project.config.texts.TextsConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DeleteAccountTexts {
    TextsConfig textsConfig=new TextsConfig();
    private String wrongPassword;

    public DeleteAccountTexts() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(textsConfig.getDeleteAccount());
        properties.load(fileReader);
        wrongPassword = (String) properties.get("wrongPassword");
    }

    public String getWrongPassword() {
        return wrongPassword;
    }
}
