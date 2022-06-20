package ir.pi.project.config.others;

import ir.pi.project.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ShowChatConfig {
    MainConfig mainConfig=new MainConfig();
    private Integer buttonWidth;
    private Integer buttonHeight;
    private Integer myMessageR;
    private Integer myMessageG;
    private Integer myMessageB;
    private Integer othersMessageR;
    private Integer othersMessageG;
    private Integer othersMessageB;
    private Integer labelWidth;
    public ShowChatConfig() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getShowChatConfig());
        properties.load(fileReader);
        buttonWidth=Integer.parseInt((String) properties.get("buttonWidth"));
        buttonHeight=Integer.parseInt((String) properties.get("buttonHeight"));
        myMessageR=Integer.parseInt((String) properties.get("myMessageR"));
        myMessageG=Integer.parseInt((String) properties.get("myMessageG"));
        myMessageB=Integer.parseInt((String) properties.get("myMessageB"));
        othersMessageR=Integer.parseInt((String) properties.get("othersMessageR"));
        othersMessageG=Integer.parseInt((String) properties.get("othersMessageG"));
        othersMessageB=Integer.parseInt((String) properties.get("othersMessageB"));
        labelWidth=Integer.parseInt((String) properties.get("labelWidth"));
    }

    public Integer getButtonWidth() {
        return buttonWidth;
    }

    public Integer getButtonHeight() {
        return buttonHeight;
    }

    public Integer getMyMessageR() {
        return myMessageR;
    }

    public Integer getMyMessageG() {
        return myMessageG;
    }

    public Integer getMyMessageB() {
        return myMessageB;
    }

    public Integer getOthersMessageR() {
        return othersMessageR;
    }

    public Integer getOthersMessageG() {
        return othersMessageG;
    }

    public Integer getOthersMessageB() {
        return othersMessageB;
    }

    public Integer getLabelWidth() {
        return labelWidth;
    }
}
