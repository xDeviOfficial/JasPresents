package pl.jasmc.presents.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.jasmc.presents.database.DatabaseConfiguration;
import pl.jasmc.presents.inventory.PresentInventory;
import pl.jasmc.presents.utils.Utils;

import java.sql.SQLException;

public class PresentCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("prezent")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.hasPermission("jasmc.admin")) {
                    if(args.length == 3 && args[0].equalsIgnoreCase("dodaj")) {
                        String presentName = args[1];
                        String presentType = args[2];
                        String location = Utils.locationToString(p.getTargetBlock(null, 200).getLocation());
                        try {
                            DatabaseConfiguration.addPresent(location, presentName, presentType);
                            p.sendMessage(ChatColor.GREEN + "Dodano prezent pomyślnie: " + presentName + "Type=" + presentType);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if(args.length == 0) {
                        PresentInventory.open(p);
                    }
                } else {
                    //NO PERMISSION - ADMIN
                }
               // ItemStack item = Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc1ZDEzY2ExNGJjYmJkMWNkZTIxYWEwNjYwMDEwMWU0NTZkMzE4YWFkZjE3OGIyNzkzNjc4YjQ5NGY2ZGNlOCJ9fX0=", "test");
               // p.getInventory().addItem(item);
            } else {
                sender.sendMessage("Ta komenda jest tylko dla Graczy");
            }
        }
        return false;
    }


    public void openGui(Player player) {

    }
}
