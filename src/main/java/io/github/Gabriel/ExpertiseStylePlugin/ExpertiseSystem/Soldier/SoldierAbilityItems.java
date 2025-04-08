package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SoldierAbilityItems extends AbilityItemTemplate {
    public SoldierAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack slash() {
        HashMap<String, Integer> damageStats = new HashMap<>() {{
            put("physical", 120);
        }};

        return ExpertiseItemTemplate.makeExpertiseAbilityItem("swordsman", "Slash", 2, "Yep.", damageStats);
    }

    public static ItemStack stab() {
        HashMap<String, Integer> damageStats = new HashMap<>() {{
            put("physical", 150);
        }};

        return ExpertiseItemTemplate.makeExpertiseAbilityItem("swordsman", "Stab", 2, "<<< but forwards.", damageStats);
    }
}