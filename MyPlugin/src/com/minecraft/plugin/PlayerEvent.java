package com.minecraft.plugin;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import java.util.ArrayList;
import java.util.Random;

public class PlayerEvent implements Listener{

    private final MyBukkitPlugin MyBukkitPlugin;

    public PlayerEvent(MyBukkitPlugin MyBukkitPlugin){
        this.MyBukkitPlugin = MyBukkitPlugin;
    }

    @EventHandler
    public void VillagerAcquireTradeEvent(VillagerAcquireTradeEvent e) {
        FileConfiguration f = MyBukkitPlugin.getConfig();
        Villager v = (Villager)e.getEntity();
        String lv = "level_"+v.getVillagerLevel();
        String recipe = rndRecipe(lv);
        ItemStack result = getResult(recipe);
        ArrayList<ItemStack> require = getRequire(recipe);

        MerchantRecipe r = new MerchantRecipe(result,0,10,true,50,(float)1.0);
        for(int i = 0;i < require.size();i++) {
            r.addIngredient(require.get(i));
        }
        e.setRecipe(r);
    }

    //隨機取得一個配方
    public String rndRecipe(String lv){
        FileConfiguration f = MyBukkitPlugin.getConfig();
        Object[] array = f.getConfigurationSection(lv).getKeys(false).toArray();
        int rnd = new Random().nextInt(array.length);
        String recipe = lv+"."+array[rnd];
        return recipe;
    }

    public ItemStack getResult(String recipe){
        FileConfiguration f = MyBukkitPlugin.getConfig();
        String result = f.getString(recipe+"."+".result");
        int cutindex = result.indexOf(",");
        String item = result.substring(0,cutindex);
        String amount = result.substring(cutindex+1);
        return new ItemStack(Material.getMaterial(item),Integer.parseInt(amount));
    }

    public ArrayList<ItemStack> getRequire(String recipe){
        FileConfiguration f = MyBukkitPlugin.getConfig();
        //取得物品List並轉成陣列,List打印後: [IRON_SWORD,1,DIAMOND,1]
        Object[] require = f.getStringList(recipe+"."+".require").toArray();

        ArrayList<ItemStack> req = new ArrayList<>();
        for(int i = 0;i < require.length;i++) {
            int cutindex = require[i].toString().indexOf(",");
            String item = require[i].toString().substring(0, cutindex);
            String amount = require[i].toString().substring(cutindex + 1);
            req.add(new ItemStack(Material.getMaterial(item), Integer.parseInt(amount)));
        }
        return req;
    }





}
