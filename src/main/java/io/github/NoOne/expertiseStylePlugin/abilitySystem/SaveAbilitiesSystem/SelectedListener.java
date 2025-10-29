package io.github.NoOne.expertiseStylePlugin.abilitySystem.SaveAbilitiesSystem;

import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SelectedListener implements Listener {
    private SelectedManager selectedManager;

    public SelectedListener(ExpertiseStylePlugin expertiseStylePlugin) {
        selectedManager = expertiseStylePlugin.getSelectedManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AbilityProfile abilityProfile = selectedManager.getPlayerProfile(player.getUniqueId());

        if (abilityProfile == null) {
            selectedManager.createnewProfile(player);
        }
    }
}
