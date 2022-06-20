package ir.pi.project.config.texts.entering;

import ir.pi.project.config.texts.TextsConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LogInTexts {
    TextsConfig textsConfig=new TextsConfig();
    private String notFound;

    public LogInTexts() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(textsConfig.getLogIn());
        properties.load(fileReader);
        notFound = (String) properties.get("notFound");
    }

    public String getNotFound() {
        return notFound;
    }
}
