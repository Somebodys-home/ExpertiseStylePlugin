package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marauder;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.UseAbilityEvent;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.NoOne.nMLItems.ItemSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class MarauderListener implements Listener {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private SelectedManager selectedManager;
    private MarauderAbilityEffects marauderAbilityEffects;

    public MarauderListener(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        selectedManager = expertiseStylePlugin.getSelectedManager();
    }

    @EventHandler
    public void onUseAbility(UseAbilityEvent event) {
        if (event.getWeapon() != null) {
            String[] selectedAbilities = selectedManager.getPlayerProfile(event.getPlayer().getUniqueId()).getSelectedAbilities().getSelectedAbilitesArray();
            String abilityName = event.getAbility().getItemMeta().getDisplayName();
            marauderAbilityEffects = new MarauderAbilityEffects(expertiseStylePlugin, event.getPlayer());

            if (Arrays.asList(selectedAbilities).contains(abilityName)) {
                if (abilityName.equals("§4§lBlade Tornado")) {
                    if (ExpertiseItemTemplate.getWeaponsForAbility(MarauderAbilityItems.bladeTornado()).contains(ItemSystem.getItemType(event.getWeapon()))) {
                        marauderAbilityEffects.bladeTornado(event.getWeapon(), event.getHotbarSlot());
                    } else {
                        event.getPlayer().sendMessage("§c⚠ §nWrong Weapon!§r§c ⚠");
                    }
                }
            }
        }
    }
}