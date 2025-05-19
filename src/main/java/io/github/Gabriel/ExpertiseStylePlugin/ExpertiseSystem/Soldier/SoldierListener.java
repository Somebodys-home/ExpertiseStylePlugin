package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.UseAbilityEvent;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.selectedSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class SoldierListener implements Listener {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private SelectedManager selectedManager;
    private SoldierAbilityEffects soldierAbilityEffects;

    public SoldierListener(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        selectedManager = expertiseStylePlugin.getSelectedManager();
    }

    // go from helditemevent -> castabilityevent -> specific listener
    @EventHandler
    public void onUseAbility(UseAbilityEvent event) {
        if (event.getWeapon() != null) {
            soldierAbilityEffects = new SoldierAbilityEffects(event.getPlayer());
            String abilityName = event.getWeapon().getItemMeta().getDisplayName();
            String[] selectedAbilities = selectedManager.getPlayerProfile(event.getPlayer().getUniqueId()).getSelectedAbilities().getAbilities();

            if (Arrays.asList(selectedAbilities).contains(abilityName)) {
                if (abilityName.contains("Slash")) {
                    soldierAbilityEffects.slash(event.getWeapon());
                }
            }
        }
    }
}