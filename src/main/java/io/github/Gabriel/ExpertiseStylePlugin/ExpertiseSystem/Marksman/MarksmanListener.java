package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marksman;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.UseAbilityEvent;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class MarksmanListener implements Listener {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private SelectedManager selectedManager;

    public MarksmanListener(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        selectedManager = expertiseStylePlugin.getSelectedManager();
    }

    @EventHandler
    public void onUseAbility(UseAbilityEvent event) {
        if (event.getWeapon() != null) {
            String[] selectedAbilities = selectedManager.getPlayerProfile(event.getPlayer().getUniqueId()).getSelectedAbilities().getSelectedAbilitiesArray();
            String abilityName = event.getAbility().getItemMeta().getDisplayName();

            if (Arrays.asList(selectedAbilities).contains(abilityName)) {
                if (ExpertiseManager.isHoldingWeaponForAbility(event)) {
                    if (abilityName.equals("§a§lRapid Shot")) MarksmanAbilityEffects.rapidShot(event.getPlayer(), event.getHotbarSlot(), event.getWeapon(), event.getAbility());
                } else {
                    event.getPlayer().sendMessage("§c⚠ §nWrong Weapon!§r§c ⚠");
                }
            }
        }
    }
}