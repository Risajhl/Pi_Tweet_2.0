package ir.pi.project.config.others;

import ir.pi.project.config.MainConfig;
import ir.pi.project.config.addresses.InfoConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LimitsConfig {
    MainConfig mainConfig=new MainConfig();
    private Integer reportLimit;
    public LimitsConfig() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getLimits());
        properties.load(fileReader);
        reportLimit=Integer.parseInt((String) properties.get("reportLimit"));
    }

    public int getReportLimit() {
        return reportLimit;
    }
}
