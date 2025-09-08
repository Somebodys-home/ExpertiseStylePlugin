package io.github.Gabriel.expertiseStylePlugin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedConfig;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin.AssassinAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin.AssassinListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Cavalier.CavalierAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Cavalier.CavalierListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseCommand;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marauder.MarauderAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marauder.MarauderListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marksman.MarksmanAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marksman.MarksmanListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.MartialArtist.MartialArtistAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.MartialArtist.MartialArtistListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ShieldHero.ShieldHeroAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ShieldHero.ShieldHeroListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierListener;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleCommand;
import io.github.NoOne.menuSystem.MenuListener;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import io.github.NoOne.nMLShields.NMLShields;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExpertiseStylePlugin extends JavaPlugin {
    private static ExpertiseStylePlugin instance;
    private NMLShields nmlShields;
    private NMLPlayerStats nmlPlayerStats;
    private SelectedManager selectedManager;
    private SelectedConfig selectedConfig;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        instance = this;

        nmlShields = JavaPlugin.getPlugin(NMLShields.class);
        nmlPlayerStats = JavaPlugin.getPlugin(NMLPlayerStats.class);

        new AbilityItemTemplate(this);
        new ExpertiseItemTemplate(this);
        new ExpertiseManager(this);
        new SoldierAbilityEffects(this);
        new AssassinAbilityEffects(this);
        new MarauderAbilityEffects(this);
        new CavalierAbilityEffects(this);
        new MartialArtistAbilityEffects(this);
        new ShieldHeroAbilityEffects(this);
        new MarksmanAbilityEffects(this);

        selectedConfig = new SelectedConfig(this, "abilities");
        selectedConfig.loadConfig();

        selectedManager = new SelectedManager(this);
        selectedManager.loadProfilesFromConfig();

        cooldownManager = new CooldownManager(this);
        cooldownManager.start();

        getCommand("expertise").setExecutor(new ExpertiseCommand(this));
        getCommand("style").setExecutor(new StyleCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new SelectedListener(this), this);
        getServer().getPluginManager().registerEvents(new AbilityListener(), this);
        getServer().getPluginManager().registerEvents(new SoldierListener(this), this);
        getServer().getPluginManager().registerEvents(new AssassinListener(this), this);
        getServer().getPluginManager().registerEvents(new MarauderListener(this), this);
        getServer().getPluginManager().registerEvents(new CavalierListener(this), this);
        getServer().getPluginManager().registerEvents(new MartialArtistListener(this), this);
        getServer().getPluginManager().registerEvents(new ShieldHeroListener(this), this);
        getServer().getPluginManager().registerEvents(new MarksmanListener(this), this);
    }

    @Override
    public void onDisable() {
        cooldownManager.stop();
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

    public NMLShields getNmlShields() {
        return nmlShields;
    }

    public NMLPlayerStats getNmlPlayerStats() {
        return nmlPlayerStats;
    }
}
