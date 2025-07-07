package io.github.Gabriel.expertiseStylePlugin;

import io.github.Gabriel.damagePlugin.DamagePlugin;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveSelectedAbilitiesSystem.SelectedConfig;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveSelectedAbilitiesSystem.SelectedListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveSelectedAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierListener;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleAbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.commands.ChooseExpertiseCommand;
import io.github.Gabriel.expertiseStylePlugin.commands.ChooseStyleCommand;
import io.github.Gabriel.menuSystem.MenuListener;
import io.github.NoOne.nMLEnergySystem.NMLEnergySystem;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExpertiseStylePlugin extends JavaPlugin {
    private static ExpertiseStylePlugin instance;
    private static DamagePlugin damagePlugin;
    private static NMLEnergySystem nmlEnergySystem;
    private SelectedManager selectedManager;
    private SelectedConfig selectedConfig;

    @Override
    public void onEnable() {
        instance = this;
        damagePlugin = JavaPlugin.getPlugin(DamagePlugin.class);
        nmlEnergySystem = JavaPlugin.getPlugin(NMLEnergySystem.class);

        new AbilityItemTemplate(this);
        new ExpertiseItemTemplate(this);
        new StyleAbilityItemTemplate(this);

        selectedConfig = new SelectedConfig(this, "profiles");
        selectedConfig.loadConfig();

        selectedManager = new SelectedManager(this);
        selectedManager.loadProfilesFromConfig();

        getCommand("chooseexpertise").setExecutor(new ChooseExpertiseCommand(this));
        getCommand("choosestyle").setExecutor(new ChooseStyleCommand(this));
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

    public static DamagePlugin getDamagePlugin() {
        System.out.println(damagePlugin);
        return damagePlugin;
    }

    public static NMLEnergySystem getNmlEnergySystem() {
        return nmlEnergySystem;
    }

    public SelectedManager getSelectedManager() {
        return selectedManager;
    }

    public SelectedConfig getSelectedConfig() {
        return selectedConfig;
    }
}
