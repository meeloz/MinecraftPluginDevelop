package com.github.skp81.tkigui.listener;

import com.github.skp81.tkigui.TkiguiPlugin;
import com.github.skp81.tkigui.data.ShowcaseData;
import com.github.skp81.tkigui.manager.GUIManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ItemBreakHandler implements Listener {
    private final GUIManager guiManager;

    public ItemBreakHandler() {
        guiManager = TkiguiPlugin.getInstance().getGUIManager();
    }


    //防止展示的掉落物实体被破坏
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

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent e) {
        HashMap<String, ShowcaseData> showcases = guiManager.getShowcases();
        for(String key : showcases.keySet()) {
            if(showcases.get(key).getHost().equals(e.getEntity().getUniqueId()))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHopperPickup(EntityPickupItemEvent e) {
        HashMap<String, ShowcaseData> showcases = guiManager.getShowcases();
        for(String key : showcases.keySet()) {
            if(showcases.get(key).getHost().equals(e.getItem().getUniqueId()))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e) {
        HashMap<String, ShowcaseData> showcases = guiManager.getShowcases();
        if (e.getCaught() instanceof Item) {
            Item item = (Item)e.getCaught();
            for (String key : showcases.keySet()) {
                if (showcases.get(key).getHost().equals(item.getUniqueId()))
                    e.setCancelled(true);
            }
        }
    }
}
