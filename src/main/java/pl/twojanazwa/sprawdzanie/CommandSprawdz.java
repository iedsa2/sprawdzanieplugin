
package pl.twojanazwa.sprawdzanie;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSprawdz implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (args.length == 1) {
            Player cel = Bukkit.getPlayerExact(args[0]);
            if (cel != null) {
                Location loc = new Location(
                    Bukkit.getWorld(Main.instancja.getRoomWorld()),
                    Main.instancja.getRoomX(),
                    Main.instancja.getRoomY(),
                    Main.instancja.getRoomZ()
                );
                cel.teleport(loc);
                p.sendMessage("§aTeleportowano gracza " + cel.getName() + " do pokoju sprawdzania.");
            } else {
                p.sendMessage("§cNie znaleziono gracza.");
            }
        } else {
            GUIHandler.openGUI(p);
        }
        return true;
    }
}
