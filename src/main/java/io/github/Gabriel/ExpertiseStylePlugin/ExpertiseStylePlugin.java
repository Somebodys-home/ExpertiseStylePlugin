package io.github.Gabriel.expertiseStylePlugin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemListener;
import io.github.Gabriel.expertiseStylePlugin.commands.ChooseExpertiseCommand;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.MenuSystem.MenuListener;
import io.github.Gabriel.expertiseStylePlugin.MenuSystem.PlayerMenuUtility;
import io.github.Gabriel.expertiseStylePlugin.commands.ChooseStyleCommand;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleAbilityItemTemplate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;

public final class ExpertiseStylePlugin extends JavaPlugin {
    private static ExpertiseStylePlugin instance;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        new AbilityItemTemplate(instance);
        new ExpertiseItemTemplate(instance);
        new StyleAbilityItemTemplate(instance);

        getCommand("chooseexpertise").setExecutor(new ChooseExpertiseCommand());
        getCommand("choosestyle").setExecutor(new ChooseStyleCommand());
        getLogger().info("chooseStyle command registered");
        getServer().getPluginManager().registerEvents(new AbilityItemListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    public static ExpertiseStylePlugin getInstance() {
        return instance;
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player player) {
        PlayerMenuUtility playerMenuUtility;

        if (playerMenuUtilityMap.containsKey(player)) {
            return playerMenuUtilityMap.get(player);
        } else {
            playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player, playerMenuUtility);
            return playerMenuUtility;
        }
    }
}
