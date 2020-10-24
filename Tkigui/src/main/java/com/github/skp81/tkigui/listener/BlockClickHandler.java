package com.github.skp81.tkigui.listener;

import com.github.skp81.tkigui.TkiguiPlugin;
import com.github.skp81.tkigui.gui.GUI;
import com.github.skp81.tkigui.manager.GUIManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BlockClickHandler implements Listener {
    private final GUIManager guiManager;

    public BlockClickHandler(){
        guiManager = TkiguiPlugin.getInstance().getGUIManager();
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;
        if(!e.getHand().equals(EquipmentSlot.HAND))
            return;
        if(!e.getClickedBlock().getType().equals(Material.RED_NETHER_BRICK_SLAB))
            return;
        if(!e.getPlayer().hasPermission("tkigui.create")){
            e.getPlayer().sendMessage("§6[§3Tkigui§6] §cYou don't have the permission");
            return;
        }
        if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STICK)){
            Block block = e.getClickedBlock();
            GUI gui = new GUI(block,guiManager);
            Inventory menu = gui.getMenu();

            //如果已经创建过展示架,把展示架的内容放进容器内
            String key = menu.getItem(8).getItemMeta().getLore().get(1);
            if(guiManager.getShowcases().get(key) != null){
                List<ItemStack> items = guiManager.getShowcases().get(key).getItems();
                for(int i = 0;i < items.size();i++)
                    menu.setItem(i,items.get(i));
            }
            e.getPlayer().openInventory(menu);
        }
    }
}




















