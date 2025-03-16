package io.github.Gabriel.expertiseStylePlugin;

import io.github.Gabriel.expertiseStylePlugin.AbilityStuff.AbilityItems;
import io.github.Gabriel.expertiseStylePlugin.AbilityStuff.AbilityListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStuff.ChooseExpertiseCommand;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStuff.Menus.Expertise.ExpertiseAbilityItems;
import io.github.Gabriel.expertiseStylePlugin.MenuSystem.MenuListener;
import io.github.Gabriel.expertiseStylePlugin.MenuSystem.PlayerMenuUtility;
import io.github.Gabriel.expertiseStylePlugin.StyleStuff.ChooseStyleCommand;
import io.github.Gabriel.expertiseStylePlugin.StyleStuff.StyleMenus.AbilityItems.StyleAbilityItems;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Objects;

public final class ExpertiseStylePlugin extends JavaPlugin {
    private static ExpertiseStylePlugin instance;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        new AbilityItems(instance);
        new ExpertiseAbilityItems(instance);
        new StyleAbilityItems(instance);

        getCommand("chooseexpertise").setExecutor(new ChooseExpertiseCommand());
        getCommand("choosestyle").setExecutor(new ChooseStyleCommand());
        getLogger().info("chooseStyle command registered");
        getServer().getPluginManager().registerEvents(new AbilityListener(this), this);
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
