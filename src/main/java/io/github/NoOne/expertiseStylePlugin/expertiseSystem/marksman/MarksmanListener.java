package io.github.NoOne.expertiseStylePlugin.expertiseSystem.marksman;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.UseAbilityEvent;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.saveAbilitiesSystem.SelectedManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MarksmanListener implements Listener {
    private SelectedManager selectedManager;

    public MarksmanListener(ExpertiseStylePlugin expertiseStylePlugin) {
        selectedManager = expertiseStylePlugin.getSelectedManager();
    }

    @EventHandler
    public void onUseAbility(UseAbilityEvent event) {
        if (event.getWeapon() != null) {
            Player player = event.getPlayer();
            int hotbarSlot = event.getHotbarSlot();
            ItemStack ability = event.getAbility();
            String[] selectedAbilities = selectedManager.getAbilityProfile(event.getPlayer().getUniqueId()).getSelectedAbilitiesArray();
            String abilityName = event.getAbility().getItemMeta().getDisplayName();

            if (Arrays.asList(selectedAbilities).contains(abilityName)) {
                switch (abilityName) {
                    case "§a§lRapid Shot" -> MarksmanAbilityEffects.rapidShot(player, hotbarSlot, ability);
                }
            }
        }
    }
}