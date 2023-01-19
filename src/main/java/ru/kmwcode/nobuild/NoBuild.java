package ru.kmwcode.nobuild;

import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoBuild extends JavaPlugin {
    LuckPerms perms;
    @Override
    public void onEnable() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if(provider != null){
            perms = provider.getProvider();
        }
        nya();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void nya(){
        Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                while (true){

                    for(Player player: Bukkit.getOnlinePlayers()){
                        User user = perms.getPlayerAdapter(Player.class).getUser(player);
                        String nya = "%playertime_time_seconds%";
                        nya = PlaceholderAPI.setPlaceholders(player, nya);
                        if(Integer.parseInt(nya) >= 86400){
                            removePermission(user, "group.cannotbuild");
                            addPermission(user, "group.canbuild");
                        }
                        player.sendMessage("теперь ты можешь строить тнт и ставить лаву");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, 20);
    }

    public void addPermission(User user, String permission){
        user.data().add(Node.builder(permission).build());
        perms.getUserManager().saveUser(user);
    }
    public void removePermission(User user, String permission){
        user.data().remove(Node.builder(permission).build());
        perms.getUserManager().saveUser(user);
    }
}
