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

public class DatabaseConfiguration {

    private static Statement stm;
    private static Presents ab = Presents.getInstance();

    public static void checkTable() throws SQLException {
        Connection connection = ab.getHikari().getConnection();
        stm = connection.createStatement();
        stm.executeUpdate("create table if not exists JasPresents (id int(2) not null AUTO_INCREMENT, location varchar(100), present_name varchar(32), present_type varchar(10), PRIMARY KEY(id));");
        stm.executeUpdate("create table if not exists JasPresentsUsers (id INT AUTO_INCREMENT, username VARCHAR(16), world varchar(32), x INT(10), y INT, z INT, present_id INT,  PRIMARY KEY(id), FOREIGN KEY(present_id) REFERENCES JasPresents(id));");

    }

    public static void purgePresentsTables(){

        String query1 = "TRUNCATE [TABLE] JasPresents";
        String query2 = "TRUNCATE [TABLE] JasPresentsUsers";
        try {
            stm.executeUpdate(query1);
            stm.executeUpdate(query2);
            DataManager.players.clear();
            if(Presents.DEBUG_MODE) {
                System.out.print("Zresetowano prezenty.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    /*
        loadPlayer()
        @p = Gracz
        Ladowanie gracza
     */

    public static void loadPlayer(Player player)  {
        JPlayer jPlayer = new JPlayer(player.getUniqueId().toString(), player.getName());
        loadPresentsToFind(jPlayer);
        loadPresentFound(jPlayer);
        DataManager.players.add(jPlayer);

    }

        /*
        loadTestPresentFound()
        @p = JPlayer obiekt
        Ladowanie znalezionych prezentow
     */

    public static void loadPresentFound(JPlayer player) {
        String query = "SELECT * FROM JasPresents INNER JOIN JasPresentsUsers ON JasPresentsUsers.present_id = JasPresents.id WHERE JasPresentsUsers.username = \"" + player.getName() + "\" ";
        try {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()) {
                String presentName = rs.getString("present_name");
                String presentType = rs.getString("present_type");
                int presentID = rs.getInt("present_id");
                String world = rs.getString("world");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");
                player.getPresentsFound().add(new Present(presentName, PresentType.valueOf(presentType.toUpperCase()), new Location(Bukkit.getWorld(world), x,y,z), presentID));
                if(Presents.DEBUG_MODE) {
                    System.out.println("Dodano: " + player.getName() + " do znalezionych [" + presentName);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

         /*
        loadTestPresentToFind()
        @p = Gracz
        Ladowanie prezentow do znalezienia
     */

    public static void loadPresentsToFind(JPlayer jPlayer) {
        String query = "select * from JasPresents where (id) NOT IN (select present_id from JasPresentsUsers where username=\"" + jPlayer.getName() + "\");";
        try {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()) {
                        int id = rs.getInt("id");
                        String location = rs.getString("location");
                        String present_name = rs.getString("present_name");
                        String present_type = rs.getString("present_type");
                        jPlayer.getPresentsToFind().add(new Present(present_name, PresentType.valueOf(present_type.toUpperCase()), Utils.stringToLoc(location), id));
                        if(Presents.DEBUG_MODE) {
                            System.out.println("Zaladowano prezent do znalezienia dla gracza: " + jPlayer.getName());
                        }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*
        SPRAWDZANIE CZY ZNALAZL PREZENT
     */


    public static boolean alreadyFound(Present present, String player) {
        String query = "SELECT * FROM JasPresentsUsers WHERE username=\"" + player + "\"";
        try (ResultSet rs = stm.executeQuery(query)) {
            while(rs.next()) {
                if(present.getId() == rs.getInt("present_id")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
        ZANALEZIENIE PREZENTU
     */


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

    /*
        Dodawanie prezentu do bazy
     */


    public static void addPresent(String location, String name, String type) throws SQLException  {
        String query = "INSERT INTO JasPresents (location, present_name, present_type) VALUES (\"" +  location + "\",\"" + name + "\",\"" + type + "\")";
        if(Presents.DEBUG_MODE) {
            System.out.println("Query: " + query);
        }

        stm.executeUpdate(query);
    }

    public static void deletePresent(String name)throws SQLException {
        String query = "DELETE FROM JasPresents WHERE name=\"" + name + "\"";
        stm.executeUpdate(query);
    }

    /*

        DEBUGOWANIE

     */
    public static void loadPresents() throws SQLException {
        ResultSet rs = stm.executeQuery("SELECT * FROM JasPresents");
        int i = 0;
        while(rs.next()) {
            Present present = new Present(rs.getString("present_name"), PresentType.valueOf(rs.getString("present_type").toUpperCase()), Utils.stringToLoc(rs.getString("location")), rs.getInt("id"));
            DataManager.addPresent(present);
            i++;
        }
        if(Presents.DEBUG_MODE) {
            System.out.println("Załadowano: " + i + " prezentow");
        }

        for(Present p : DataManager.loadedPresenents) {
            if(Presents.DEBUG_MODE) {
                System.out.println("Kolor: " + p.getType().name());
            }

        }
    }


}
