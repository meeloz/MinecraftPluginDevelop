package com.github.skp81.tkigui.manager;

import com.github.skp81.tkigui.TkiguiPlugin;
import com.github.skp81.tkigui.data.ShowcaseData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.IOException;
import java.util.*;

public class GUIManager {
    TkiguiPlugin instance;
    DataManager dataManager;
    HashMap<String, ShowcaseData> showcases;

    public GUIManager(){
        instance = TkiguiPlugin.getInstance();
        showcases = new HashMap<>();
        dataManager = instance.getDataManager();
        showItem();
    }


    public void onButton(Player player, String key, List<ItemStack> items) throws IOException, InvalidConfigurationException {
        //检查展示架是否已经被创建过
        if(showcases.get(key) == null){
            //将key的内容转换成Location
            Location loc = getLocation(key);
            //丢出要展示的掉落物
            Item dropitem = player.getWorld().dropItem(loc.clone().add(0.5,1.2,0.5),items.get(0));
            dropitem.setVelocity(new Vector());
            //建立展示架资料
            ShowcaseData data = new ShowcaseData(dropitem.getUniqueId(),loc,items);
            showcases.put(key,data);
            player.sendMessage("§6[§3Tkigui§6] §aShowcase has been created!");
        }else{
            player.sendMessage("§6[§3Tkigui§6] §cYou already create a showcase.");
        }
        //将展示架的资料保存到data.yml
        TkiguiPlugin.getInstance().getDataManager().writeFile(key);
    }

    //将key转化成坐标
    public Location getLocation(String key){
        String[] str = key.split(",");
        World world = Bukkit.getWorld(str[0]);
        Double x = Double.parseDouble(str[1]);
        Double y = Double.parseDouble(str[2]);
        Double z = Double.parseDouble(str[3]);
        return new Location(world,x,y,z);
    }

    //定时更换如果掉的内容,GUIManager实例化时启动
    public void showItem(){
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            public void run() {
                Set<String> keys = showcases.keySet();
                if(!keys.isEmpty()) {
                    for (String key : keys) {
                        List<ItemStack> items = showcases.get(key).getItems();
                        Item item = (Item) Bukkit.getEntity(showcases.get(key).getHost());
                        //如果展示品(掉落物)遗失,重新生成掉落物并更新展示架.data.yml的内容
                        if(item == null){
                            Location loc = showcases.get(key).getLocation();
                            item = loc.getWorld().dropItem(loc.clone().add(0.5,1.2,0.5),items.get(0));
                            item.setVelocity(new Vector());
                            showcases.get(key).setHost(item.getUniqueId());
                            try {
                                dataManager.writeFile(key);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InvalidConfigurationException e) {
                                e.printStackTrace();
                            }
                        }
                        int rnd = new Random().nextInt(items.size());
                        if(!items.isEmpty()){
                            item.setItemStack(items.get(rnd));
                        }
                    }
                }
            }
        }, 100L,100L);
    }

    public HashMap<String, ShowcaseData> getShowcases(){
        return showcases;
    }
}
