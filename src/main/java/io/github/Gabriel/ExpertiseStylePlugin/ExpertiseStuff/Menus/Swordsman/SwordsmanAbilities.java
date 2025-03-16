package io.github.Gabriel.expertiseStylePlugin.ExpertiseStuff.Menus.Swordsman;

import io.github.Gabriel.expertiseStylePlugin.AbilityStuff.AbilityItems;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStuff.Menus.Expertise.ExpertiseAbilityItems;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.inventory.ItemStack;

public class SwordsmanAbilities extends AbilityItems {
    public SwordsmanAbilities() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack slash() {
        return ExpertiseAbilityItems.makeExpertiseAbilityItem("swordsman", "Slash", 2, "It's just a sword slash");
    }

    public static ItemStack stab() {
        return ExpertiseAbilityItems.makeExpertiseAbilityItem("swordsman", "Stab", 2, "Well imagine a slash, but forwards. Yeah.");
    }
}