package pl.jasmc.presents.objects;

import org.bukkit.Bukkit;
import pl.jasmc.presents.Presents;
import pl.jasmc.presents.database.DatabaseConfiguration;
import pl.jasmc.presents.managers.DataManager;

import java.util.ArrayList;
import java.util.Iterator;
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
        //loadPresents();
    }

    public void loadPresents() {



        for(Present present : DataManager.loadedPresenents) {

            if(alreadyFound(present)) {
                try {
                    Present presentClone = (Present) present.clone();
                    this.presentsFound.add(presentClone);
                    System.out.println("Dodano: " + presentClone.getName() + " do znalezionych prezentow " + name);
                    return;
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Present presentClone = (Present) present.clone();
                    this.presentsToFind.add(presentClone);
                    System.out.println("Dodano: " + presentClone.getName() + " do znalezienia dla " + name);
                    return;
                }catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void updatePresents() {
        Iterator itr = getPresentsFound().iterator();
        while(itr.hasNext()) {
            Present currentPresent = (Present)itr.next();
            if(currentPresent.getLocation() != null) {
                if(currentPresent.getLocation().getWorld().getName().equalsIgnoreCase(Presents.WORLD_NAME)) {
                    Presents.placeSkull(Bukkit.getPlayer(name), currentPresent.getLocation(), "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc1ZDEzY2ExNGJjYmJkMWNkZTIxYWEwNjYwMDEwMWU0NTZkMzE4YWFkZjE3OGIyNzkzNjc4YjQ5NGY2ZGNlOCJ9fX0=");
                }
            }
        }

        Iterator itrToFind = getPresentsToFind().iterator();
        while(itrToFind.hasNext()) {
            Present currentPresentToFind = (Present)itrToFind.next();
            if(currentPresentToFind.getLocation() != null) {
                if(currentPresentToFind.getLocation().getWorld().getName().equalsIgnoreCase(Presents.WORLD_NAME)) {
                    Presents.placeSkull(Bukkit.getPlayer(name), currentPresentToFind.getLocation(), currentPresentToFind.getTextureID());
                }

            }

        }
    }


    public void foundPresent(Present present) {
        this.presentsFound.add(present);
        this.presentsToFind.remove(present);
        DatabaseConfiguration.foundPresent(this.name, present);
    }

    public boolean alreadyFound(Present present) {
        return DatabaseConfiguration.alreadyFound(present, name);
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
