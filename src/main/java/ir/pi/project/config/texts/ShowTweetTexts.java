package ir.pi.project.config.texts;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ShowTweetTexts {
    TextsConfig textsConfig=new TextsConfig();
    private String noComment;
    private String saved;
    private String reported;
    private String commented;

    public ShowTweetTexts() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(textsConfig.getShowTweet());
        properties.load(fileReader);
        noComment = (String) properties.get("noComment");
        saved = (String) properties.get("saved");
        reported = (String) properties.get("reported");
        commented = (String) properties.get("commented");
    }

    public String getNoComment() { return noComment; }
    public String getSaved() { return saved; }
    public String getReported() { return reported; }
    public String getCommented() { return commented; }
}
