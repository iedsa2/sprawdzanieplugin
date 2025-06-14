package me.iedsa2.sprawdzanie;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin implements Listener {

    private Location sprawdzanieRoom;
    private final Map<String, Location> originalLocations = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Plugin Sprawdzanie zostal wlaczony!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda tylko dla graczy!");
            return true;
        }

        Player admin = (Player) sender;

        switch (cmd.getName().toLowerCase()) {
            case "setroomsprawdzanie":
                sprawdzanieRoom = admin.getLocation();
                admin.sendMessage(ChatColor.GREEN + "Ustawiono pokój sprawdzania!");
                break;
            case "sprawdz":
                if (args.length == 0) {
                    openSprawdzanieGUI(admin);
                } else {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target != null && sprawdzanieRoom != null) {
                        originalLocations.put(target.getName(), target.getLocation());
                        target.teleport(sprawdzanieRoom);
                        admin.sendMessage(ChatColor.YELLOW + "Gracz " + target.getName() + " został przeniesiony do pokoju sprawdzania.");
                    } else {
                        admin.sendMessage(ChatColor.RED + "Nie znaleziono gracza lub pokój nie jest ustawiony.");
                    }
                }
                break;
            case "przyznajesie":
                Bukkit.broadcastMessage(ChatColor.YELLOW + "[Sprawdzanie] Gracz " + admin.getName() + " przyznał się – kara 5 dni.");
                break;
        }
        return true;
    }

    private void openSprawdzanieGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, "Sprawdzanie");

        gui.setItem(1, createItem(Material.RED_CONCRETE, "Brak wspolpracy"));
        gui.setItem(4, createItem(Material.EMERALD_BLOCK, "Wykryto cheaty"));
        gui.setItem(7, createItem(Material.BOOK, "Przyznanie sie"));

        player.openInventory(gui);
    }

    private ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Sprawdzanie")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            String msg = null;

            switch (event.getSlot()) {
                case 1:
                    msg = "Brak wspolpracy – kara 12 dni.";
                    break;
                case 4:
                    msg = "Wykryto cheaty – kara 10 dni.";
                    break;
                case 7:
                    msg = "Przyznanie sie – kara 5 dni.";
                    break;
            }

            if (msg != null) {
                Bukkit.broadcastMessage(ChatColor.RED + "[Sprawdzanie] " + player.getName() + ": " + msg);
                player.closeInventory();
            }
        }
    }
}
