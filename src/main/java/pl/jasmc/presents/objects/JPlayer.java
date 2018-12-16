package pl.jasmc.presents.objects;

import pl.jasmc.presents.database.DatabaseConfiguration;
import pl.jasmc.presents.managers.DataManager;

import java.util.ArrayList;
import java.util.List;

public class JPlayer {

    public String UUID;
    public String name;
    public List<Present> presentsFound;
    public List<Present> presentsToFind;


    public JPlayer(String uuid, String name) {
        this.UUID = uuid;
        this.name = name;
        this.presentsToFind = new ArrayList<>();
        this.presentsFound = new ArrayList<>();
        for(Present present : DataManager.loadedPresenents) {
            this.presentsToFind.add(present);
        }
    }

    public void loadPresents() {

    }





    public void foundPresent(Present present) {
        this.presentsFound.add(present);
        DatabaseConfiguration.foundPresent(this.name, present);
    }

    public boolean presentAlreadyFound(Present present) {
        if(presentsFound.contains(present)) {
            return true;
        }
        return false;
    }

    public String getUUID() {
        return UUID;
    }

    public String getName() {
        return name;
    }

    public List<Present> getPresentsFound() {
        return presentsFound;
    }

    public List<Present> getPresentsToFind() {
        return presentsToFind;
    }

}
