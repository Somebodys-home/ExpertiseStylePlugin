package io.github.NoOne.expertiseStylePlugin.abilitySystem.saveAbilitiesSystem;

import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectedManager {
    private static Map<UUID, SelectedAbilities> profileMap = new HashMap<>(); // hashmap of all the profiles of all the players online atm
    private FileConfiguration config;
    private SelectedConfig profileConfig;

    public SelectedManager(ExpertiseStylePlugin expertiseStylePlugin) {
        profileConfig = expertiseStylePlugin.getSelectedConfig();
        config = profileConfig.getConfig();
    }

    public void createnewProfile(Player player) {
        SelectedAbilities selectedAbilities = new SelectedAbilities(
                player.getInventory().getItem(0).getItemMeta().getDisplayName(),
                player.getInventory().getItem(1).getItemMeta().getDisplayName(),
                player.getInventory().getItem(2).getItemMeta().getDisplayName(),
                player.getInventory().getItem(3).getItemMeta().getDisplayName());

        profileMap.put(player.getUniqueId(), selectedAbilities);

    }

    public SelectedAbilities getAbilityProfile(UUID uuid) {
        return profileMap.get(uuid);
    }

    public void addAbilityProfile(UUID uuid, SelectedAbilities abilityProfile) {
        profileMap.put(uuid, abilityProfile);
    }

    public void loadProfilesFromConfig() {
        for (String id : config.getConfigurationSection("").getKeys(false)) {
            UUID uuid = UUID.fromString(id);
            String style1 = config.getString(id + ".abilities.style");
            String expertise1 = config.getString(id + ".abilities.expertise1");
            String expertise2 = config.getString(id + ".abilities.expertise2");
            String expertise3 = config.getString(id + ".abilities.expertise3");

            SelectedAbilities selectedAbilities = new SelectedAbilities(style1, expertise1, expertise2, expertise3);

            profileMap.put(uuid, selectedAbilities);
        }
    }

    public void saveProfilesToConfig() {
        for (UUID uuid : profileMap.keySet()) {
            String id = uuid.toString();
            SelectedAbilities selectedAbilities = profileMap.get(uuid);

            config.set(id + ".abilities.style", selectedAbilities.getStyle());
            config.set(id + ".abilities.expertise1", selectedAbilities.getExpertise1());
            config.set(id + ".abilities.expertise2", selectedAbilities.getExpertise2());
            config.set(id + ".abilities.expertise3", selectedAbilities.getExpertise3());
        }
    }

    public void saveAProfileToConfig(Player player) {
        String id = player.getUniqueId().toString();
        SelectedAbilities selectedAbilities = profileMap.get(player.getUniqueId());

        config.set(id + ".abilities.style", selectedAbilities.getStyle());
        config.set(id + ".abilities.expertise1", selectedAbilities.getExpertise1());
        config.set(id + ".abilities.expertise2", selectedAbilities.getExpertise2());
        config.set(id + ".abilities.expertise3", selectedAbilities.getExpertise3());
    }
}
