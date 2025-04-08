package io.github.Gabriel.expertiseStylePlugin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.selectedSystem.SelectedConfig;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.selectedSystem.SelectedListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.selectedSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierListener;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleAbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.commands.ChooseExpertiseCommand;
import io.github.Gabriel.expertiseStylePlugin.commands.ChooseStyleCommand;
import io.github.Gabriel.menuSystem.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExpertiseStylePlugin extends JavaPlugin {
    private static ExpertiseStylePlugin instance;
    private SelectedManager selectedManager;
    private SelectedConfig selectedConfig;

    @Override
    public void onEnable() {
        instance = this;
        new AbilityItemTemplate(instance);
        new ExpertiseItemTemplate(instance);
        new StyleAbilityItemTemplate(instance);

        selectedConfig = new SelectedConfig(this, "profiles");
        selectedConfig.loadConfig();

        selectedManager = new SelectedManager(this);
        selectedManager.loadProfilesFromConfig();

        getCommand("chooseexpertise").setExecutor(new ChooseExpertiseCommand(this));
        getCommand("choosestyle").setExecutor(new ChooseStyleCommand(this));
        getCommand("test").setExecutor(new test(this));
        getServer().getPluginManager().registerEvents(new SelectedListener(this), this);
        getServer().getPluginManager().registerEvents(new AbilityItemListener(this), this);
        getServer().getPluginManager().registerEvents(new SoldierListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        // DO NOT CHANGE THE ORDER OF THIS, IT WILL BREAK
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
