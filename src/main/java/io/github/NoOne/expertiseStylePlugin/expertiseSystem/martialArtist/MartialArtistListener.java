package io.github.NoOne.expertiseStylePlugin.expertiseSystem.martialArtist;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.UseAbilityEvent;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.saveAbilitiesSystem.SelectedManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MartialArtistListener implements Listener {
    private SelectedManager selectedManager;

    public MartialArtistListener(ExpertiseStylePlugin expertiseStylePlugin) {
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
                    case "§4§l10-Hit Combo" -> MartialArtistAbilityEffects.tenHitCombo(player, hotbarSlot);
                }
            }
        }
    }
}