package pl.jasmc.presents.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.jasmc.presents.objects.JPlayer;
import pl.jasmc.presents.objects.Present;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DataManager {

    public static List<Present> loadedPresenents = new ArrayList<>();
    public static List<JPlayer> players = new ArrayList<>();



    public static void addPresent(Present present) {
        loadedPresenents.add(present);
    }


    public static void removePlayer(Player p) {
        players.remove(getJPlayer(p.getName()));
    }

    public static JPlayer getJPlayer(String name) {
        for(JPlayer jplayer : players) {
            if(jplayer.getName().equalsIgnoreCase(name)) {
                return jplayer;
            }
        }
        return null;
    }


    public static Present getPresentByLocation(Location location) {
        for(Present present : loadedPresenents) {
            if(present.getLocation().equals(location)){
                return present;
            }
        }
        return null;
    }

    public static Present getSinglePresentByLocation(Location location, JPlayer player) {
        Iterator itr = player.getPresentsFound().iterator();
        if(itr.hasNext()) {
            Present current = (Present) itr.next();
            if(current.getLocation().equals(location)) {
                return current;
            }
        }

        Iterator itr2 = player.getPresentsToFind().iterator();
        if(itr2.hasNext()) {
            Present current = (Present) itr2.next();
            if(current.getLocation().equals(location)) {
                return current;
            }
        }
        return null;
    }





}
