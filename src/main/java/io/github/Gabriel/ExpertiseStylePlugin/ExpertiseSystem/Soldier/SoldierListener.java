package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.UseAbilityEvent;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.NoOne.nMLItems.ItemSystem;
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

    @EventHandler
    public void onUseAbility(UseAbilityEvent event) {
        if (event.getWeapon() != null) {
            String[] selectedAbilities = selectedManager.getPlayerProfile(event.getPlayer().getUniqueId()).getSelectedAbilities().getSelectedAbilitiesArray();
            String abilityName = event.getAbility().getItemMeta().getDisplayName();
            soldierAbilityEffects = new SoldierAbilityEffects(expertiseStylePlugin, event.getPlayer(), event.getHotbarSlot());

            if (Arrays.asList(selectedAbilities).contains(abilityName)) {
                if (ExpertiseManager.isHoldingWeaponForAbility(event)) {
                    if (abilityName.equals("§c§lSlash")) soldierAbilityEffects.slash(event.getWeapon());
                } else {
                    event.getPlayer().sendMessage("§c⚠ §nWrong Weapon!§r§c ⚠");
                }
            }
        }
    }
}