package ir.pi.project.config.addresses;

import ir.pi.project.config.MainConfig;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class InfoConfig {
    AddressesConfig addressesConfig=new AddressesConfig();

    private String usersDirectory;
    private String tweetsDirectory;
    private String groupsDirectory;
    private String groupChatsDirectory;
    private String messagesDirectory;
    private String imagesDirectory;

    public InfoConfig() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(addressesConfig.getInfo());
        properties.load(fileReader);
        usersDirectory = (String) properties.get("usersDirectory");
        tweetsDirectory = (String) properties.get("tweetsDirectory");
        groupsDirectory = (String) properties.get("groupsDirectory");
        groupChatsDirectory = (String) properties.get("groupChatsDirectory");
        messagesDirectory = (String) properties.get("messagesDirectory");
        imagesDirectory = (String) properties.get("imagesDirectory");
    }


    public String getUsersDirectory() { return usersDirectory; }

    public String getTweetsDirectory() { return tweetsDirectory; }

    public String getGroupsDirectory() { return groupsDirectory; }

    public String getGroupChatsDirectory() { return groupChatsDirectory; }

    public String getMessagesDirectory() { return messagesDirectory; }

    public String getImagesDirectory() { return imagesDirectory; }
}
