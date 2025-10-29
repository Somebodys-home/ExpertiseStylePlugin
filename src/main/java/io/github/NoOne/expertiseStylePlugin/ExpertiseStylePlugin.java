package io.github.NoOne.expertiseStylePlugin;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityListener;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.CooldownSystem.CooldownManager;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.SaveAbilitiesSystem.SelectedConfig;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.SaveAbilitiesSystem.SelectedListener;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.annulled.AnnulledAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.annulled.AnnulledListener;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.assassin.AssassinAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.assassin.AssassinListener;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.cavalier.CavalierAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.cavalier.CavalierListener;
import io.github.NoOne.expertiseStylePlugin.commands.ExpertiseCommand;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseManager;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.hallowed.HallowedAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.hallowed.HallowedListener;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.marauder.MarauderAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.marauder.MarauderListener;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.marksman.MarksmanAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.marksman.MarksmanListener;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.martialArtist.MartialArtistAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.martialArtist.MartialArtistListener;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.primordial.PrimordialAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.primordial.PrimordialListener;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.shieldHero.ShieldHeroAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.shieldHero.ShieldHeroListener;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.soldier.SoldierAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.soldier.SoldierListener;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.sorcerer.SorcererAbilityEffects;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.sorcerer.SorcererListener;
import io.github.NoOne.menuSystem.MenuListener;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import io.github.NoOne.nMLShields.GuardingSystem;
import io.github.NoOne.nMLShields.NMLShields;
import io.github.NoOne.nMLSkills.NMLSkills;
import io.github.NoOne.nMLSkills.skillSetSystem.SkillSetManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExpertiseStylePlugin extends JavaPlugin {
    private static ExpertiseStylePlugin instance;
    private ProfileManager profileManager;
    private SkillSetManager skillSetManager;
    private GuardingSystem guardingSystem;
    private SelectedManager selectedManager;
    private SelectedConfig selectedConfig;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        instance = this;

        profileManager = JavaPlugin.getPlugin(NMLPlayerStats.class).getProfileManager();
        skillSetManager = JavaPlugin.getPlugin(NMLSkills.class).getSkillSetManager();
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

    public SkillSetManager getSkillSetManager() {
        return skillSetManager;
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
