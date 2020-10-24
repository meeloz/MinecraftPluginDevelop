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

            //获取容器内前7格的物品内容(后面2格是按钮)
            List<ItemStack> items = new ArrayList<>();
            for(int i=0;i < 7;i++){
                if(e.getInventory().getItem(i) != null){
                    items.add(e.getInventory().getItem(i));
                }
            }

            //如果前7格的物品内容皆为空则结束事件
            if(!items.isEmpty()){
                String key = e.getInventory().getItem(8).getItemMeta().getLore().get(1);
                guiManager.onButton((Player) e.getWhoClicked(),key,items);
                return;
            }
            e.getWhoClicked().sendMessage("§6[§3Tkigui§6] §cYou must put at least an item!");
            return;
        }
    }

    @EventHandler
    public void onDelete(InventoryClickEvent e) throws IOException, InvalidConfigurationException {
        if(!e.getView().getTitle().equals("§r§b[CustomDisplayGUI]"))
            return;
        if(e.getRawSlot() == 7){
            e.setCancelled(true);

            //检查是否有展示架可以删除
            String key = e.getInventory().getItem(8).getItemMeta().getLore().get(1);
            if(guiManager.getShowcases().get(key) == null){
                e.getWhoClicked().sendMessage("§6[§3Tkigui§6] §cYou didn't set any showcase here.");
                return;
            }

            //删除展示架.展示品(掉落物)实体
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

        //获取容器内前7格的物品内容(后面2格是按钮)
        String key = e.getInventory().getItem(8).getItemMeta().getLore().get(1);
        List<ItemStack> items = new ArrayList<>();
        for(int i=0;i < 7;i++){
            if(e.getInventory().getItem(i) != null){
                items.add(e.getInventory().getItem(i));
            }
        }

        if(guiManager.getShowcases().get(key) == null){
            //如果前7格不是空的且未建立展示架,将前7格的物品放回玩家身上
            if(!items.isEmpty()){
                for(ItemStack item:items){
                    e.getPlayer().getInventory().addItem(item);
                }
            }
        }else{
            //如果前7格不是空的但已经建立展示架,更新展示架的内容为前7格的物品
            if(!items.isEmpty()){
                guiManager.getShowcases().get(key).getItems().clear();
                guiManager.getShowcases().get(key).getItems().addAll(items);
                TkiguiPlugin.getInstance().getDataManager().writeFile(key);
            }else{
                //如果前7格是空的,删除展示架.展示品(掉落物)实体
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
