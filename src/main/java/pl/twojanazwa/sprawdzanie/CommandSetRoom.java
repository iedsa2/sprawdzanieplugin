
package pl.twojanazwa.sprawdzanie;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetRoom implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        Main.instancja.setRoomLocation(p.getWorld().getName(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
        p.sendMessage("§aUstawiono lokalizację pokoju sprawdzania.");
        return true;
    }
}
