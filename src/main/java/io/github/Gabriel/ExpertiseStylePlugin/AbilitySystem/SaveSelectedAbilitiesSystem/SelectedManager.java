package io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveSelectedAbilitiesSystem;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectedManager {
    private static Map<UUID, AbilityProfile> profileMap = new HashMap<>(); // hashmap of all the profiles of all the players online atm
    private FileConfiguration config;
    private SelectedConfig profileConfig;

    public SelectedManager(ExpertiseStylePlugin expertiseStylePlugin) {
        profileConfig = expertiseStylePlugin.getSelectedConfig();
        config = profileConfig.getConfig();
    }

    public AbilityProfile createnewProfile(Player player) {
        SelectedAbilities selectedAbilities = new SelectedAbilities(
                player.getInventory().getItem(0).getItemMeta().getDisplayName(),
                player.getInventory().getItem(1).getItemMeta().getDisplayName(),
                player.getInventory().getItem(2).getItemMeta().getDisplayName(),
                player.getInventory().getItem(3).getItemMeta().getDisplayName());
        AbilityProfile abilityProfile = new AbilityProfile(selectedAbilities);

        profileMap.put(player.getUniqueId(), abilityProfile);

        return abilityProfile;
    }

    public AbilityProfile getPlayerProfile(UUID uuid) {
        return profileMap.get(uuid);
    }

    public void loadProfilesFromConfig() {
        for (String id : config.getConfigurationSection("").getKeys(false)) {
            UUID uuid = UUID.fromString(id);
            String style1 = config.getString(id + ".abilities.style1");
            String style2 = config.getString(id + ".abilities.style2");
            String expertise1 = config.getString(id + ".abilities.expertise1");
            String expertise2 = config.getString(id + ".abilities.expertise2");

            SelectedAbilities selectedAbilities = new SelectedAbilities(style1, style2, expertise1, expertise2);
            AbilityProfile abilityProfile = new AbilityProfile(selectedAbilities);

            profileMap.put(uuid, abilityProfile);
        }
    }

    public void saveProfilesToConfig() {
        for (UUID uuid : profileMap.keySet()) {
            String id = uuid.toString();
            AbilityProfile abilityProfile = profileMap.get(uuid);
            SelectedAbilities selectedAbilities = abilityProfile.getSelectedAbilities();

            config.set(id + ".abilities.style1", selectedAbilities.getStyle1());
            config.set(id + ".abilities.style2", selectedAbilities.getStyle2());
            config.set(id + ".abilities.expertise1", selectedAbilities.getExpertise1());
            config.set(id + ".abilities.expertise2", selectedAbilities.getExpertise2());
        }
    }

    public void saveAProfileToConfig(Player player) {
        String id = player.getUniqueId().toString();
        AbilityProfile abilityProfile = profileMap.get(player.getUniqueId());
        SelectedAbilities selectedAbilities = abilityProfile.getSelectedAbilities();

        config.set(id + ".abilities.style1", selectedAbilities.getStyle1());
        config.set(id + ".abilities.style2", selectedAbilities.getStyle2());
        config.set(id + ".abilities.expertise1", selectedAbilities.getExpertise1());
        config.set(id + ".abilities.expertise2", selectedAbilities.getExpertise2());
    }
}
