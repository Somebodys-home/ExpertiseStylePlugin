package io.github.Gabriel.expertiseStylePlugin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.selectedSystem.SelectedConfig;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.selectedSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierListener;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleAbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.commands.ChooseExpertiseCommand;
import io.github.Gabriel.expertiseStylePlugin.commands.ChooseStyleCommand;
import io.github.Gabriel.menuSystem.MenuSystem;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExpertiseStylePlugin extends JavaPlugin {
    private static ExpertiseStylePlugin instance;
    private static MenuSystem menuSystem;
    private SelectedManager selectedManager;
    private SelectedConfig selectedConfig;

    @Override
    public void onEnable() {
        new AbilityItemTemplate(instance);
        new ExpertiseItemTemplate(instance);
        new StyleAbilityItemTemplate(instance);
        instance = this;
        menuSystem = (MenuSystem) getServer().getPluginManager().getPlugin("MenuSystem");

        selectedConfig = new SelectedConfig(this, "profiles");
        selectedConfig.loadConfig();

        selectedManager = new SelectedManager(this);
        selectedManager.loadProfilesFromConfig();
        getCommand("chooseexpertise").setExecutor(new ChooseExpertiseCommand());
        getCommand("choosestyle").setExecutor(new ChooseStyleCommand());
        getCommand("testability").setExecutor(new testabilitycommand());

        getServer().getPluginManager().registerEvents(new AbilityItemListener(this), this);
        getServer().getPluginManager().registerEvents(new SoldierListener(), this);
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
