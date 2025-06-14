
package pl.twojanazwa.sprawdzanie;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPrzyznajeSie implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        Bukkit.broadcast("§cGracz §e" + p.getName() + " §cprzyznał się do cheatów! (kara: 5 dni)", "minecraft.command.op");
        return true;
    }
}
