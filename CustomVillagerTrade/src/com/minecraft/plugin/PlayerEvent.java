package com.minecraft.plugin;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;


public class PlayerEvent implements Listener{

    @EventHandler
    public void VillagerAcquireTradeEvent(VillagerAcquireTradeEvent e){
        Recipe Merchant = new Recipe((Villager)e.getEntity());
        e.setRecipe(Merchant.getMerchant());
    }

    @EventHandler
    public void VillagerReplenishTradeEvent(VillagerReplenishTradeEvent e){
        Recipe Merchant = new Recipe((Villager)e.getEntity());
        e.getEntity().getRecipes().clear();
        e.setRecipe(Merchant.getMerchant());
    }

}
