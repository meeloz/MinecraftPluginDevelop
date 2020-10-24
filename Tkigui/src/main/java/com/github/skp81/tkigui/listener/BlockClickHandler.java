package com.github.skp81.tkigui.listener;

import com.github.skp81.tkigui.TkiguiPlugin;
import com.github.skp81.tkigui.gui.GUI;
import com.github.skp81.tkigui.manager.GUIManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Set;

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
            e.getPlayer().openInventory(menu);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Set<String> keys = guiManager.getShowcases().keySet();
        for(String key:keys){
            if(e.getBlockPlaced().getLocation().equals(guiManager.getLocation(key))){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§6[§3Tkigui§6] §cYou cannot do this.");
                return;
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Set<String> keys = guiManager.getShowcases().keySet();
        for(String key:keys){
            if(e.getBlock().getLocation().equals(guiManager.getLocation(key))){
                e.setCancelled(true);
                e.getPlayer().sendMessage("§6[§3Tkigui§6] §cYou cannot do this.");
                return;
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e){
        List<Block> blocks = e.blockList();
        Set<String> keys = guiManager.getShowcases().keySet();
        for(Block block:blocks){
            for(String key:keys){
                if(block.getLocation().equals(guiManager.getShowcases().get(key).getLocation())){
                    e.blockList().clear();
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e){
        Set<String> keys = guiManager.getShowcases().keySet();
        for(String key:keys){
            if(e.getBlock().getLocation().equals(guiManager.getShowcases().get(key).getLocation())){
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent e){
        Set<String> keys = guiManager.getShowcases().keySet();
        for(String key:keys){
            if(e.getBlockClicked().getLocation().equals(guiManager.getShowcases().get(key).getLocation())){
                e.setCancelled(true);
                return;
            }
        }
    }
}




















