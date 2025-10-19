package io.github.Gabriel.expertiseStylePlugin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedConfig;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedListener;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Annulled.AnnulledAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Annulled.AnnulledListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin.AssassinAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin.AssassinListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Cavalier.CavalierAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Cavalier.CavalierListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseCommand;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Hallowed.HallowedAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Hallowed.HallowedListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marauder.MarauderAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marauder.MarauderListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marksman.MarksmanAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marksman.MarksmanListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.MartialArtist.MartialArtistAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.MartialArtist.MartialArtistListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Primordial.PrimordialAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Primordial.PrimordialListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ShieldHero.ShieldHeroAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ShieldHero.ShieldHeroListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierListener;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Sorcerer.SorcererAbilityEffects;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Sorcerer.SorcererListener;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleCommand;
import io.github.NoOne.menuSystem.MenuListener;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import io.github.NoOne.nMLShields.GuardingSystem;
import io.github.NoOne.nMLShields.NMLShields;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExpertiseStylePlugin extends JavaPlugin {
    private static ExpertiseStylePlugin instance;
    private ProfileManager profileManager;
    private GuardingSystem guardingSystem;
    private SelectedManager selectedManager;
    private SelectedConfig selectedConfig;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        instance = this;

        profileManager = JavaPlugin.getPlugin(NMLPlayerStats.class).getProfileManager();
        guardingSystem = JavaPlugin.getPlugin(NMLShields.class).getGuardingSystem();

        selectedConfig = new SelectedConfig(this, "abilities");
        selectedConfig.loadConfig();

        selectedManager = new SelectedManager(this);
        selectedManager.loadProfilesFromConfig();

        cooldownManager = new CooldownManager(this);
        cooldownManager.start();

        new AbilityItemTemplate(this);
        new ExpertiseAbilityItemTemplate(this);
        new ExpertiseManager(this);
        new SoldierAbilityEffects(this);
        new AssassinAbilityEffects(this);
        new MarauderAbilityEffects(this);
        new CavalierAbilityEffects(this);
        new MartialArtistAbilityEffects(this);
        new ShieldHeroAbilityEffects(this);
        new MarksmanAbilityEffects(this);
        new SorcererAbilityEffects(this);
        new PrimordialAbilityEffects(this);
        new HallowedAbilityEffects(this);
        new AnnulledAbilityEffects(this);

        getCommand("expertise").setExecutor(new ExpertiseCommand(this));
        getCommand("style").setExecutor(new StyleCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new SelectedListener(this), this);
        getServer().getPluginManager().registerEvents(new AbilityListener(this), this);
        getServer().getPluginManager().registerEvents(new SoldierListener(this), this);
        getServer().getPluginManager().registerEvents(new AssassinListener(this), this);
        getServer().getPluginManager().registerEvents(new MarauderListener(this), this);
        getServer().getPluginManager().registerEvents(new CavalierListener(this), this);
        getServer().getPluginManager().registerEvents(new MartialArtistListener(this), this);
        getServer().getPluginManager().registerEvents(new ShieldHeroListener(this), this);
        getServer().getPluginManager().registerEvents(new MarksmanListener(this), this);
        getServer().getPluginManager().registerEvents(new SorcererListener(this), this);
        getServer().getPluginManager().registerEvents(new PrimordialListener(this), this);
        getServer().getPluginManager().registerEvents(new HallowedListener(this), this);
        getServer().getPluginManager().registerEvents(new AnnulledListener(this), this);
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

    public GuardingSystem getGuardingSystem() {
        return guardingSystem;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }
}
