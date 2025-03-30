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
        return ExpertiseItemTemplate.makeExpertiseAbilityItem("swordsman", "Slash", 2, "It's just a sword slash");
    }

    public static ItemStack stab() {
        return ExpertiseItemTemplate.makeExpertiseAbilityItem("swordsman", "Stab", 2, "Well imagine a slash, but forwards. Yeah.");
    }
}