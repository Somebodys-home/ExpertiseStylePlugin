package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ShieldHero;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.UseAbilityEvent;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
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