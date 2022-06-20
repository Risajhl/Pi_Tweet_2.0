package ir.pi.project.config.texts.myPage;

import ir.pi.project.config.texts.TextsConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MyPageTexts {
    TextsConfig textsConfig=new TextsConfig();
    private String noTweets;
    private String noBio;
    public MyPageTexts() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(textsConfig.getMyPage());
        properties.load(fileReader);
        noBio = (String) properties.get("noBio");
        noTweets = (String) properties.get("noTweets");
    }

    public String getNoTweets() {
        return noTweets;
    }

    public String getNoBio() {
        return noBio;
    }
}
