package com.minecraft.plugin;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import java.util.ArrayList;


public class PlayerEvent implements Listener{
    CustomVillagerTrade instance = CustomVillagerTrade.getInstance();

    @EventHandler
    public void VillagerAcquireTradeEvent(VillagerAcquireTradeEvent e){
        VillagerRecipe VillagerRecipe = new VillagerRecipe((Villager)e.getEntity());

        int exp = VillagerRecipe.getExp();
        ItemStack result = VillagerRecipe.getResult();
        ArrayList<ItemStack> require = VillagerRecipe.getReq();

        MerchantRecipe r = new MerchantRecipe(result,0,10,true,exp,(float)1.0);
        for(int i = 0;i < require.size();i++) {
            r.addIngredient(require.get(i));
        }
        e.setRecipe(r);
    }
}
