package ir.pi.project.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Model {

    public int id;

    static private final Logger logger= LogManager.getLogger(User.class);
    public Model(){
        try {
            File lastId = new File("./src/main/resources/lastId");
            Scanner sc = new Scanner(lastId);
            int q = sc.nextInt();
            this.id = q;
            FileOutputStream fout = new FileOutputStream(lastId, false);

            PrintStream out = new PrintStream(fout);
            q++;
            out.println(q);
            out.flush();
            out.close();


        } catch (FileNotFoundException e) {
            logger.warn("New model could not be made");
            e.printStackTrace();
        }

    }

    public int getId() {
        return id;
    }
}
