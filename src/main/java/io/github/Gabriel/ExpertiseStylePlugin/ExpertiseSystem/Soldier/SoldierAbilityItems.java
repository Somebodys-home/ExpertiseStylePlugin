package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import org.bukkit.inventory.ItemStack;

public class SoldierAbilityItems extends AbilityItemTemplate {
    public SoldierAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack slash() {
        return ExpertiseItemTemplate.makeExpertiseAbilityItem(
                "swordsman",
                "Slash",
                "You get the idea, right?",
                "Area",
                2,
                0,
                2,
                15,
                120, null);
    }
}