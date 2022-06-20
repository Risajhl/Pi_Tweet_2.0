package ir.pi.project.config.texts;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainMenuTexts {
    TextsConfig textsConfig=new TextsConfig();
    private String emptyTimeLine;

    public MainMenuTexts() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(textsConfig.getMainMenu());
        properties.load(fileReader);
        emptyTimeLine = (String) properties.get("emptyTimeLine");
    }

    public String getEmptyWorld() {
        return emptyTimeLine;
    }
}
