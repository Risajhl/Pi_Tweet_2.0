package ir.pi.project.config.addresses;

import ir.pi.project.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AddressesConfig {

    private MainConfig mainConfigPath=new MainConfig();
    private String info;
    private String fxml;

    public AddressesConfig() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfigPath.getAddresses());
        properties.load(fileReader);
        info = (String) properties.get("info");
        fxml = (String) properties.get("fxml");

    }

    public String getInfo() {
        return info;
    }
    public String getFxml() { return fxml; }
}
