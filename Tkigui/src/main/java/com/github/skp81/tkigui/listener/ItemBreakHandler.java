package com.github.skp81.tkigui.listener;

import com.github.skp81.tkigui.TkiguiPlugin;
import com.github.skp81.tkigui.data.ShowcaseData;
import com.github.skp81.tkigui.manager.GUIManager;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.HashMap;

public class ItemBreakHandler implements Listener {
    private final GUIManager guiManager;

    public ItemBreakHandler() {
        guiManager = TkiguiPlugin.getInstance().getGUIManager();
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
