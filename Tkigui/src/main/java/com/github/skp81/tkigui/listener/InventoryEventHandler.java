package com.github.skp81.tkigui.listener;

import com.github.skp81.tkigui.TkiguiPlugin;
import com.github.skp81.tkigui.manager.DataManager;
import com.github.skp81.tkigui.manager.GUIManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryEventHandler implements Listener {
    private final GUIManager guiManager;
    private final DataManager dataManager;

    public InventoryEventHandler() {
        guiManager = TkiguiPlugin.getInstance().getGUIManager();
        dataManager = TkiguiPlugin.getInstance().getDataManager();
    }


    @EventHandler
    public void onCreate(InventoryClickEvent e) throws IOException, InvalidConfigurationException {
        if(!e.getView().getTitle().equals("§r§b[CustomDisplayGUI]"))
            return;
        if(e.getRawSlot() == 8){
            e.setCancelled(true);

            List<ItemStack> items = new ArrayList<>();

            for(int i=0;i < 7;i++){
                if(e.getInventory().getItem(i) != null){
                    items.add(e.getInventory().getItem(i));
                }
            }

            if(!items.isEmpty()){
                String key = e.getInventory().getItem(8).getItemMeta().getLore().get(1);
                guiManager.onButton((Player) e.getWhoClicked(),key,items);
                return;
            }
            e.getWhoClicked().sendMessage("§6[§3Tkigui§6] §cYou must put at least an item!");
        }
    }

    @EventHandler
    public void onDelete(InventoryClickEvent e) throws IOException, InvalidConfigurationException {
        if(!e.getView().getTitle().equals("§r§b[CustomDisplayGUI]"))
            return;
        if(e.getRawSlot() == 7){
            e.setCancelled(true);
            String key = e.getInventory().getItem(8).getItemMeta().getLore().get(1);
            if(guiManager.getShowcases().get(key) == null){
                e.getWhoClicked().sendMessage("§6[§3Tkigui§6] §cYou didn't set any showcase here.");
                return;
            }
            UUID uuid = guiManager.getShowcases().get(key).getHost();
            Bukkit.getEntity(uuid).remove();
            guiManager.getShowcases().remove(key);
            dataManager.deleteFile(key);
            e.getView().close();
            e.getWhoClicked().sendMessage("§6[§3Tkigui§6] §dSuccess delete showcase!");
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) throws IOException, InvalidConfigurationException {
        if(!e.getView().getTitle().equals("§r§b[CustomDisplayGUI]"))
            return;
        String key = e.getInventory().getItem(8).getItemMeta().getLore().get(1);
        List<ItemStack> items = new ArrayList<>();
        for(int i=0;i < 7;i++){
            if(e.getInventory().getItem(i) != null){
                items.add(e.getInventory().getItem(i));
            }
        }
        if(guiManager.getShowcases().get(key) == null){
            if(!items.isEmpty()){
                for(ItemStack item:items){
                    e.getPlayer().getInventory().addItem(item);
                }
            }
        }else{
            if(!items.isEmpty()){
                guiManager.getShowcases().get(key).getItems().clear();
                guiManager.getShowcases().get(key).getItems().addAll(items);
                TkiguiPlugin.getInstance().getDataManager().writeFile(key);
            }else{
                UUID uuid = guiManager.getShowcases().get(key).getHost();
                Bukkit.getEntity(uuid).remove();
                guiManager.getShowcases().remove(key);
                dataManager.deleteFile(key);
                e.getView().close();
                e.getPlayer().sendMessage("§6[§3Tkigui§6] §dSuccess delete showcase!");
            }
        }
    }
}
