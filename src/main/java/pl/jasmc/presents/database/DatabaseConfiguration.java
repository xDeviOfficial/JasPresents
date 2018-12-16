package pl.jasmc.presents.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.jasmc.presents.Presents;
import pl.jasmc.presents.enums.PresentType;
import pl.jasmc.presents.managers.DataManager;
import pl.jasmc.presents.objects.JPlayer;
import pl.jasmc.presents.objects.Present;
import pl.jasmc.presents.utils.ThreadPool;
import pl.jasmc.presents.utils.Utils;

import java.sql.*;
import java.util.List;

public class DatabaseConfiguration {

    private static Statement stm;
    private static Presents ab = Presents.getInstance();

    public static void checkTable() throws SQLException {
        Connection connection = ab.getHikari().getConnection();
        stm = connection.createStatement();
        stm.executeUpdate("create table if not exists JasPresents (id int(2) not null AUTO_INCREMENT, location varchar(100), present_name varchar(32), present_type varchar(10), PRIMARY KEY(id));");
        stm.executeUpdate("create table if not exists JasPresentsUsers (id INT AUTO_INCREMENT, username VARCHAR(16), world varchar(32), x INT(10), y INT, z INT, present_id INT,  PRIMARY KEY(id), FOREIGN KEY(present_id) REFERENCES JasPresents(id));");

    }

    public static void loadPlayer(Player player)  {
        JPlayer jPlayer = new JPlayer(player.getUniqueId().toString(), player.getName());
        DataManager.players.add(jPlayer);
        String query = "SELECT * FROM JasPresentsUsers WHERE username=\"" + player.getName() + "\"";
        try  {
            ResultSet resultSet = stm.executeQuery(query);
            if(resultSet.isClosed()) {
                System.out.println("Cannot load player " + player.getName() + ", ResultSet closed");
                return;
            }
            while(resultSet.next()) {

                Present present = DataManager.getPresentByLocation(new Location(Bukkit.getWorld(resultSet.getString("world")), resultSet.getInt("x"), resultSet.getInt("y"), resultSet.getInt("z")));
                if(alreadyFound(present)) {

                    jPlayer.getPresentsFound().add(present);
                } else {
                    jPlayer.getPresentsToFind().add(present);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean alreadyFound(Present present) {
        String query = "SELECT * FROM JasPresents WHERE id=\"" + present.getId() + "\"";
        try (ResultSet rs = stm.executeQuery(query)) {
            if(rs.next()) {
                boolean alreadyFound =  present.getId() == rs.getInt("id");
                return alreadyFound;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void foundPresent(String player, Present present) {
        String query  = "INSERT INTO JasPresentsUsers (username, world, x, y, z, present_id) VALUES (\"" + player + "\",\"" + present.getLocation().getWorld().getName() + "\"," +  present.getLocation().getX() + ","  + present.getLocation().getY() + "," + present.getLocation().getZ() + "," + present.getId() + ")";
        ThreadPool.runTaskAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    stm.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void addPresent(String location, String name, String type) throws SQLException  {
        String query = "INSERT INTO JasPresents (location, present_name, present_type) VALUES (\"" +  location + "\",\"" + name + "\",\"" + type + "\")";
        System.out.println("Query: " + query);
        stm.executeUpdate(query);
    }


    public static void loadPresents() throws SQLException {
        ResultSet rs = stm.executeQuery("SELECT * FROM JasPresents");
        int i = 0;
        while(rs.next()) {

            Present present = new Present(rs.getString("present_name"), PresentType.valueOf(rs.getString("present_type").toUpperCase()), Utils.stringToLoc(rs.getString("location")), rs.getInt("id"));
            DataManager.addPresent(present);
            i++;
        }

        System.out.println("Załadowano: " + i + " prezentow");
        for(Present p : DataManager.loadedPresenents) {
            System.out.println("Kolor: " + p.getType().name());
        }
    }


}
