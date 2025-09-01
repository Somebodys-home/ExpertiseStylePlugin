package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.UseAbilityEvent;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.NoOne.nMLItems.ItemSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class AssassinListener implements Listener {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private SelectedManager selectedManager;
    private AssassinAbilityEffects assassinAbilityEffects;

    public AssassinListener(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        selectedManager = expertiseStylePlugin.getSelectedManager();
    }

    @EventHandler
    public void onUseAbility(UseAbilityEvent event) {
        if (event.getWeapon() != null) {
            String[] selectedAbilities = selectedManager.getPlayerProfile(event.getPlayer().getUniqueId()).getSelectedAbilities().getSelectedAbilitesArray();
            String abilityName = event.getAbility().getItemMeta().getDisplayName();
            assassinAbilityEffects = new AssassinAbilityEffects(expertiseStylePlugin, event.getPlayer());

            if (Arrays.asList(selectedAbilities).contains(abilityName)) {
                if (abilityName.equals("§8§lSlash & Dash")) {
                    if (ExpertiseItemTemplate.getWeaponsForAbility(AssassinAbilityItems.slashandDash()).contains(ItemSystem.getItemType(event.getWeapon()))) {
                        assassinAbilityEffects.slashAndDash(event.getWeapon());
                    } else {
                        event.getPlayer().sendMessage("§c⚠ §nWrong Weapon!§r§c ⚠");
                    }
                }
            }
        }
    }
}