package io.github.NoOne.expertiseStylePlugin.expertiseSystem.primordial;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.UseAbilityEvent;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.saveAbilitiesSystem.SelectedManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PrimordialListener implements Listener {
    private SelectedManager selectedManager;

    public PrimordialListener(ExpertiseStylePlugin expertiseStylePlugin) {
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
                    case "§2§lChuck Rock" -> PrimordialAbilityEffects.chuckRock(player, hotbarSlot);
                    case "§2§lPumpkin Bomb" -> PrimordialAbilityEffects.pumpkinBomb(player, hotbarSlot);
                    case "§2§lAir Ball" -> PrimordialAbilityEffects.airBall(player, hotbarSlot);
                }
            }
        }
    }
}