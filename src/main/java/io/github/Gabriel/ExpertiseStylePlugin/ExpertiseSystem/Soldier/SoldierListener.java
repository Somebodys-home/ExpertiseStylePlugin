package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CastAbilityEvent;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.selectedSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class SoldierListener implements Listener {
    private SelectedManager selectedManager;
    private SoldierAbilityEffects soldierAbilityEffects;

    public SoldierListener(ExpertiseStylePlugin expertiseStylePlugin) {
        selectedManager = expertiseStylePlugin.getSelectedManager();
    }

    @EventHandler
    public void onUseAbility(CastAbilityEvent event) {
        if (event.getAbilityItem() != null) {
            soldierAbilityEffects = new SoldierAbilityEffects(event.getPlayer());
            String abilityName = event.getAbilityItem().getItemMeta().getDisplayName();
            String[] selectedAbilities = selectedManager.getPlayerProfile(event.getPlayer().getUniqueId()).getSelectedAbilities().getAbilities();

            if (Arrays.asList(selectedAbilities).contains(abilityName)) {
                if (abilityName.contains("Slash")) {
                    soldierAbilityEffects.slash();
                }
            }
        }
    }
}