package io.github.Gabriel.expertiseStylePlugin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
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

    @Override
    public void onEnable() {
        instance = this;
        menuSystem = (MenuSystem) getServer().getPluginManager().getPlugin("MenuSystem");
        new AbilityItemTemplate(instance);
        new ExpertiseItemTemplate(instance);
        new StyleAbilityItemTemplate(instance);

        getCommand("chooseexpertise").setExecutor(new ChooseExpertiseCommand());
        getCommand("choosestyle").setExecutor(new ChooseStyleCommand());
        getCommand("testability").setExecutor(new testabilitycommand());

        getServer().getPluginManager().registerEvents(new AbilityItemListener(this), this);
        getServer().getPluginManager().registerEvents(new SoldierListener(), this);
    }

    public static ExpertiseStylePlugin getInstance() {
        return instance;
    }

    public static MenuSystem getMenuSystem() {
        return menuSystem;
    }
}
