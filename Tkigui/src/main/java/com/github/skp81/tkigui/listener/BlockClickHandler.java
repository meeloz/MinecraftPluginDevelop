package com.github.skp81.tkigui.listener;

import com.github.skp81.tkigui.TkiguiPlugin;
import com.github.skp81.tkigui.gui.GUI;
import com.github.skp81.tkigui.manager.GUIManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

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
        if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STICK)){
            Block block = e.getClickedBlock();
            GUI gui = new GUI(block,guiManager);
            Inventory menu = gui.getMenu();
            e.getPlayer().openInventory(menu);
        }
    }


}




















