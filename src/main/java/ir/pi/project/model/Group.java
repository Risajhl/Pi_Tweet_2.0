package ir.pi.project.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Group extends Model{
    static private final Logger logger= LogManager.getLogger(Group.class);

    private int owner;
    private String name;
    private List<Integer> members;
    public Group(String name, int ownerId){
            this.name=name;
            this.owner=ownerId;
            this.members=new ArrayList<>();
        }



    public int getId() {
        return id;
    }

    public int getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getMembers() {
        return members;
    }


}
