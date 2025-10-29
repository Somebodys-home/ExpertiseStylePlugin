package io.github.NoOne.expertiseStylePlugin.expertiseSystem.shieldHero;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.UseAbilityEvent;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ShieldHeroListener implements Listener {
    private SelectedManager selectedManager;

    public ShieldHeroListener(ExpertiseStylePlugin expertiseStylePlugin) {
        selectedManager = expertiseStylePlugin.getSelectedManager();
    }

    @EventHandler
    public void onUseAbility(UseAbilityEvent event) {
        Player player = event.getPlayer();
        int hotbarSlot = event.getHotbarSlot();
        ItemStack ability = event.getAbility();
        String[] selectedAbilities = selectedManager.getPlayerProfile(event.getPlayer().getUniqueId()).getSelectedAbilities().getSelectedAbilitiesArray();
        String abilityName = event.getAbility().getItemMeta().getDisplayName();

        if (Arrays.asList(selectedAbilities).contains(abilityName)) {
            switch (abilityName) {
                case "§3§lSecond Wind" -> ShieldHeroAbilityEffects.secondWind(player, hotbarSlot);
            }
        }
    }
}