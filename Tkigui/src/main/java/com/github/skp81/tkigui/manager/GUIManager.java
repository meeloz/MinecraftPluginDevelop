package com.github.skp81.tkigui.manager;

import com.github.skp81.tkigui.TkiguiPlugin;
import com.github.skp81.tkigui.data.ShowcaseData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.*;

public class GUIManager {
    TkiguiPlugin instance;
    DataManager dataManager;
    HashMap<String, ShowcaseData> showcases;

    public GUIManager(){
        instance = TkiguiPlugin.getInstance();
        showcases = new HashMap<>();
        dataManager = instance.getDataManager();
        showItem();
    }

    public void onButton(Player player, String key, List<ItemStack> items) throws IOException, InvalidConfigurationException {
        if(showcases.get(key) == null){
            Location loc = getLocation(key);
            Item dropitem = player.getWorld().dropItem(loc.clone().add(0.5,1.2,0.5),items.get(0));
            dropitem.setVelocity(new Vector());
            ShowcaseData data = new ShowcaseData(dropitem.getUniqueId(),loc,items);
            showcases.put(key,data);
            player.sendMessage("§6[§3Tkigui§6] §aShowcase has been created!");
        }else{
            player.sendMessage("§6[§3Tkigui§6] §cYou already create a showcase.");
        }
        TkiguiPlugin.getInstance().getDataManager().writeFile(key);
    }

    public Location getLocation(String key){
        String[] str = key.split(",");
        World world = Bukkit.getWorld(str[0]);
        Double x = Double.parseDouble(str[1]);
        Double y = Double.parseDouble(str[2]);
        Double z = Double.parseDouble(str[3]);
        return new Location(world,x,y,z);
    }

    public void showItem(){
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            public void run() {
                Set<String> keys = showcases.keySet();
                if(!keys.isEmpty()) {
                    for (String key : keys) {
                        List<ItemStack> items = showcases.get(key).getItems();
                        Item item = (Item) Bukkit.getEntity(showcases.get(key).getHost());
                        if(item == null){
                            Location loc = showcases.get(key).getLocation();
                            item = loc.getWorld().dropItem(loc.clone().add(0.5,1.2,0.5),items.get(0));
                            item.setVelocity(new Vector());
                            showcases.get(key).setHost(item.getUniqueId());
                            try {
                                dataManager.writeFile(key);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InvalidConfigurationException e) {
                                e.printStackTrace();
                            }
                        }
                        int rnd = new Random().nextInt(items.size());
                        if(!items.isEmpty()){
                            item.setItemStack(items.get(rnd));
                        }
                    }
                }
            }
        }, 100L,100L);
    }

    public HashMap<String, ShowcaseData> getShowcases(){
        return showcases;
    }
}
