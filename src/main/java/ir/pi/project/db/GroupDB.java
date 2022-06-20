package ir.pi.project.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.model.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.LinkedList;

public class GroupDB implements DBSet<Group>{
    static private final Logger logger= LogManager.getLogger(GroupDB.class);
    private InfoConfig infoConfig;

    {
        try {
            infoConfig = new InfoConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Group get(int id) {
        try {
//            File directory=new File("./src/main/resources/Info/Groups");
            File directory=new File(infoConfig.getGroupsDirectory());
            File Data = new File(directory, id + ".json");
            System.out.println(id+"~~~");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Data));
            logger.info("group file "+id+ " opened");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Group group=gson.fromJson(bufferedReader, Group.class);
            bufferedReader.close();
            return group;
        }
        catch (IOException e) {

            logger.warn("group with id: "+id+" could not be found in getByID");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LinkedList<Group> all() {
        return null;
    }


    @Override
    public void update(Group group) {
        try {
//            File directory=new File("./src/main/resources/Info/Groups");
            File directory=new File(infoConfig.getGroupsDirectory());

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            File Data = new File(directory, group.getId() + ".json");
            if (!Data.exists())
                Data.createNewFile();
            FileWriter writer = new FileWriter(Data);
            writer.write(gson.toJson(group));
            writer.flush();
            writer.close();
            logger.info("group with id "+group.getId()+" saved");
        } catch (IOException e) {
            logger.warn("group with id: "+group.getId()+" could not be saved in group update");
            e.printStackTrace();
        }
    }
}
