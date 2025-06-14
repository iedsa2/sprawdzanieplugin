
package pl.twojanazwa.sprawdzanie;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static Main instancja;
    private FileConfiguration config;

    private String roomWorld;
    private double roomX;
    private double roomY;
    private double roomZ;

    public static HashMap<UUID, UUID> celSprawdzany = new HashMap<>();

    @Override
    public void onEnable() {
        instancja = this;
        this.config = getConfig();
        saveDefaultConfig();
        loadRoomLocation();

        getCommand("sprawdz").setExecutor(new CommandSprawdz());
        getCommand("setroomsprawdzanie").setExecutor(new CommandSetRoom());
        getCommand("przyznajesie").setExecutor(new CommandPrzyznajeSie());

        Bukkit.getPluginManager().registerEvents(new GUIHandler(), this);
    }

    public void setRoomLocation(String world, double x, double y, double z) {
        config.set("room.world", world);
        config.set("room.x", x);
        config.set("room.y", y);
        config.set("room.z", z);
        saveConfig();
        loadRoomLocation();
    }

    private void loadRoomLocation() {
        this.roomWorld = config.getString("room.world");
        this.roomX = config.getDouble("room.x");
        this.roomY = config.getDouble("room.y");
        this.roomZ = config.getDouble("room.z");
    }

    public String getRoomWorld() { return roomWorld; }
    public double getRoomX() { return roomX; }
    public double getRoomY() { return roomY; }
    public double getRoomZ() { return roomZ; }
}
