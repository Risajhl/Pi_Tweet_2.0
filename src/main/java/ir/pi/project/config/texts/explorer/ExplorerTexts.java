package ir.pi.project.config.texts.explorer;

import ir.pi.project.config.texts.TextsConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ExplorerTexts {
    TextsConfig textsConfig=new TextsConfig();
    private String emptyWorld;
    private String itsU;
    private String notFound;


    public ExplorerTexts() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(textsConfig.getExplorer());
        properties.load(fileReader);
        emptyWorld = (String) properties.get("emptyWorld");
        itsU = (String) properties.get("itsU");
        notFound = (String) properties.get("notFound");
    }

    public String getEmptyWorld() {
        return emptyWorld;
    }

    public String getItsU() {
        return itsU;
    }

    public String getNotFound() {
        return notFound;
    }
}
