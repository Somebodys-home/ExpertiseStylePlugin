package io.github.NoOne.expertiseStylePlugin.expertiseSystem.marauder;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.UseAbilityEvent;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MarauderListener implements Listener {
    private SelectedManager selectedManager;

    public MarauderListener(ExpertiseStylePlugin expertiseStylePlugin) {
        selectedManager = expertiseStylePlugin.getSelectedManager();
    }

    @EventHandler
    public void onUseAbility(UseAbilityEvent event) {
        if (event.getWeapon() != null) {
            Player player = event.getPlayer();
            int hotbarSlot = event.getHotbarSlot();
            ItemStack ability = event.getAbility();
            String[] selectedAbilities = selectedManager.getPlayerProfile(event.getPlayer().getUniqueId()).getSelectedAbilities().getSelectedAbilitiesArray();
            String abilityName = event.getAbility().getItemMeta().getDisplayName();

            if (Arrays.asList(selectedAbilities).contains(abilityName)) {
                switch (abilityName) {
                    case "§4§lBlade Tornado" -> MarauderAbilityEffects.bladeTornado(player, hotbarSlot);
                }
            }
        }
    }
}