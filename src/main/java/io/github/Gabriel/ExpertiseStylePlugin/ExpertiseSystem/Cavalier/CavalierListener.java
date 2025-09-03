package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Cavalier;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.UseAbilityEvent;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.NoOne.nMLItems.ItemSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;

public class CavalierListener implements Listener {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private SelectedManager selectedManager;
    private CavalierAbilityEffects cavalierAbilityEffects;

    public CavalierListener(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        selectedManager = expertiseStylePlugin.getSelectedManager();
    }

    @EventHandler
    public void onUseAbility(UseAbilityEvent event) {
        if (event.getWeapon() != null) {
            String[] selectedAbilities = selectedManager.getPlayerProfile(event.getPlayer().getUniqueId()).getSelectedAbilities().getSelectedAbilitesArray();
            String abilityName = event.getAbility().getItemMeta().getDisplayName();
            cavalierAbilityEffects = new CavalierAbilityEffects(expertiseStylePlugin, event.getPlayer());

            if (Arrays.asList(selectedAbilities).contains(abilityName)) {
                if (abilityName.equals("§9§lSeismic Slam")) {
                    if (ExpertiseItemTemplate.getWeaponsForAbility(CavalierAbilityItems.seismicSlam()).contains(ItemSystem.getItemType(event.getWeapon()))) {
                        cavalierAbilityEffects.seismicSlam(event.getWeapon(), event.getHotbarSlot());
                    } else {
                        event.getPlayer().sendMessage("§c⚠ §nWrong Weapon!§r§c ⚠");
                    }
                }
            }
        }
    }
}