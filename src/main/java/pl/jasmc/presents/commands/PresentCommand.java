package pl.jasmc.presents.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.jasmc.presents.Presents;
import pl.jasmc.presents.database.DatabaseConfiguration;
import pl.jasmc.presents.enums.PresentType;
import pl.jasmc.presents.inventory.PresentInventory;
import pl.jasmc.presents.managers.DataManager;
import pl.jasmc.presents.utils.Utils;

import java.sql.SQLException;

public class PresentCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("prezent")) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.hasPermission("group.developer") || p.hasPermission("group.admin") || p.hasPermission("group.minidev") || p.hasPermission("group.wlasciciel")) {
                    if(args.length == 0) {
                        p.sendMessage(Utils.color("&c&lWykryto uprawnienia Administratora"));
                        p.sendMessage(Utils.color("&d Prezenty &eJasMC"));
                        p.sendMessage(Utils.color("&eDostepne komendy: "));
                        p.sendMessage(Utils.color("&d/&eprezent otworz &7- Otwiera GUI z prezentami znalezionymi i nie znalezionymi"));
                        p.sendMessage(Utils.color("&d/&eprezent dodaj <Nazwa> <Typ> &7- Tworzy nowy prezent i dodaje go do bazy danych"));
                        p.sendMessage(Utils.color("&d/&eprezent usun  &7- Usuwa prezent na ktory sie patrzysz"));
                        p.sendMessage(Utils.color("&d/&eprezent typy &7- Wyswietla wszystkie dostepne typy prezentow"));
                    } else if(args.length == 3 && args[0].equalsIgnoreCase("dodaj")) {
                        String presentName = args[1];
                        String presentType = args[2];
                        String location = Utils.locationToString(p.getLocation().add(0,0.01,0));
                        if(!containsEnum(presentType.toUpperCase())) {
                            p.sendMessage(Utils.color("Wystapil blad, nie podano prawidlowego typu, uzyj /prezent typy aby uzyskac liste typow"));
                            return false;
                        }
                        try {
                            DatabaseConfiguration.addPresent(location, presentName, presentType);
                            p.sendMessage(ChatColor.GREEN + "Dodano prezent pomyślnie: " + presentName + "Type=" + presentType);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else if(args.length == 1 && args[0].equalsIgnoreCase("otworz")) {
                        PresentInventory.open(p);
                    } else if(args.length == 1 && args[0].equalsIgnoreCase("typy")) {
                        p.sendMessage(Utils.color("&eDostepne tryby prezentow:"));
                        for(PresentType type : PresentType.values()) {
                            p.sendMessage(Utils.color("&bTyp: " + type.toString()));
                        }
                    } else if(args.length == 1 && args[0].equalsIgnoreCase("usun")) {
                        Location location = p.getTargetBlock(null, 10).getLocation();
                        try {
                            DatabaseConfiguration.deletePresent(DataManager.getSinglePresentByLocation(p.getLocation(), DataManager.getJPlayer(p.getName())).getName());
                            p.sendMessage(Utils.color(Presents.TAG + "&ePomyslnie usuniteto prezent, prosze o przelogowanie!"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                } else if(sender.hasPermission("jasmc.gracz")) {
                    if(args.length == 0) {
                        PresentInventory.open(p);
                    } else {
                        p.sendMessage(Utils.color(Presents.TAG + "&cPoprawne uzycie: /prezent"));
                    }

                }
               // ItemStack item = Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjc1ZDEzY2ExNGJjYmJkMWNkZTIxYWEwNjYwMDEwMWU0NTZkMzE4YWFkZjE3OGIyNzkzNjc4YjQ5NGY2ZGNlOCJ9fX0=", "test");
               // p.getInventory().addItem(item);
            } else {
                sender.sendMessage("Ta komenda jest tylko dla Graczy");
            }
        }
        return false;
    }

    public static boolean containsEnum(String text) {

        for (PresentType type : PresentType.values()) {
            if (type.name().equals(text)) {
                return true;
            }
        }

        return false;
    }


    public void openGui(Player player) {

    }
}
