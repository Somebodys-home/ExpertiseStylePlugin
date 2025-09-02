package io.github.Gabriel.expertiseStylePlugin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedConfig;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin.AssassinListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Cavalier.CavalierListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseCommand;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marauder.MarauderListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierListener;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleAbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleCommand;
import io.github.NoOne.menuSystem.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExpertiseStylePlugin extends JavaPlugin {
    private static ExpertiseStylePlugin instance;
    private SelectedManager selectedManager;
    private SelectedConfig selectedConfig;

    @Override
    public void onEnable() {
        instance = this;

        new AbilityItemTemplate(this);
        new ExpertiseItemTemplate(this);
        new StyleAbilityItemTemplate(this);

        selectedConfig = new SelectedConfig(this, "abilities");
        selectedConfig.loadConfig();

        selectedManager = new SelectedManager(this);
        selectedManager.loadProfilesFromConfig();

        getCommand("expertise").setExecutor(new ExpertiseCommand(this));
        getCommand("style").setExecutor(new StyleCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new SelectedListener(this), this);
        getServer().getPluginManager().registerEvents(new AbilityItemListener(this), this);
        getServer().getPluginManager().registerEvents(new SoldierListener(this), this);
        getServer().getPluginManager().registerEvents(new AssassinListener(this), this);
        getServer().getPluginManager().registerEvents(new MarauderListener(this), this);
        getServer().getPluginManager().registerEvents(new CavalierListener(this), this);
    }

    @Override
    public void onDisable() {
        selectedManager.saveProfilesToConfig();
        selectedConfig.saveConfig();
    }

    public static ExpertiseStylePlugin getInstance() {
        return instance;
    }

    public SelectedManager getSelectedManager() {
        return selectedManager;
    }

    public SelectedConfig getSelectedConfig() {
        return selectedConfig;
    }
}
