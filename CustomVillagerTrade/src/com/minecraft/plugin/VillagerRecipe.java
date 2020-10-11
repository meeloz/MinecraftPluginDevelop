package com.minecraft.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VillagerRecipe{
    CustomVillagerTrade instance = CustomVillagerTrade.getInstance();

    private Villager villager;
    private String Recipe;
    private String level;
    private VillagerProfession profession;
    private int exp;

    public VillagerRecipe(Villager villager){
        this.villager = villager;
        this.profession = instance.getConfig(villager.getProfession().toString());
        this.level = "Level_"+ villager.getVillagerLevel();
        this.Recipe = rndRecipe(villager.getVillagerLevel());
        this.exp = profession.getFile().getInt(level+"."+Recipe+"."+".Exp");
    }

    //随机挑选一个配方
    public String rndRecipe(int level){
        level = level-1;
        int rnd = new Random().nextInt(profession.getRecipe().get(level).size());
        return profession.getRecipe().get(level).get(rnd);
    }


    public ItemStack getResult(){
        String result = profession.getFile().getString(level+"."+Recipe+"."+".Result");
        int cutindex = result.indexOf(",");
        String item = result.substring(0,cutindex);
        String amount = result.substring(cutindex+1);
        return new ItemStack(Material.getMaterial(item),Integer.parseInt(amount));
    }

    public ArrayList<ItemStack> getReq() {
        //取得物品List並轉成陣列,List打印後: [IRON_SWORD,1,DIAMOND,1]
        ArrayList<ItemStack> req = new ArrayList<>();
        Object[] section = profession.getFile().getStringList(level + "." + Recipe + "." + ".Require").toArray();
        for (Object key : section) {
            int cutindex = key.toString().indexOf(",");
            String item = key.toString().substring(0, cutindex);
            String amount = key.toString().substring(cutindex + 1);
            req.add(new ItemStack(Material.getMaterial(item), Integer.parseInt(amount)));
        }
        return req;
    }

    public int getExp(){
        return exp;
    }
}
