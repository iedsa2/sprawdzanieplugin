
package pl.twojanazwa.sprawdzanie;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

public class GUIHandler implements Listener {
    public static void openGUI(Player p) {
        Inventory gui = Bukkit.createInventory(null, 9, "Wybierz akcję");

        gui.setItem(2, createItem(Material.GREEN_WOOL, "§aBrak współpracy"));
        gui.setItem(4, createItem(Material.RED_WOOL, "§cWykryto cheaty"));
        gui.setItem(6, createItem(Material.YELLOW_WOOL, "§ePrzyznanie się"));

        p.openInventory(gui);
    }

    private static ItemStack createItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals("Wybierz akcję")) return;
        e.setCancelled(true);
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player admin = (Player) e.getWhoClicked();
        UUID celUUID = Main.celSprawdzany.get(admin.getUniqueId());
        if (celUUID == null) {
            admin.sendMessage("§cBrak zapisanego celu sprawdzania.");
            return;
        }

        OfflinePlayer cel = Bukkit.getOfflinePlayer(celUUID);
        if (cel == null) {
            admin.sendMessage("§cNie znaleziono gracza.");
            return;
        }

        if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()) return;
        String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

        Instant teraz = Instant.now();
        switch (name) {
            case "Brak współpracy":
                cel.banPlayer("Brak współpracy podczas sprawdzania", Date.from(teraz.plus(12, ChronoUnit.DAYS)), admin.getName(), true);
                admin.sendMessage("§cZbanowano " + cel.getName() + " na 12 dni (brak współpracy).");
                break;
            case "Wykryto cheaty":
                cel.banPlayer("Wykryto cheaty", Date.from(teraz.plus(10, ChronoUnit.DAYS)), admin.getName(), true);
                admin.sendMessage("§cZbanowano " + cel.getName() + " na 10 dni (cheaty).");
                break;
            case "Przyznanie się":
                cel.banPlayer("Przyznał się do cheatów", Date.from(teraz.plus(5, ChronoUnit.DAYS)), admin.getName(), true);
                admin.sendMessage("§eZbanowano " + cel.getName() + " na 5 dni (przyznanie się).");
                break;
        }

        admin.closeInventory();
        Main.celSprawdzany.remove(admin.getUniqueId());
    }
}
