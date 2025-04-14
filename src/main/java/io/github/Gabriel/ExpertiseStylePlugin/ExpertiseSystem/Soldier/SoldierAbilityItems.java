package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.damagePlugin.customDamage.DamageType;
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
        HashMap<DamageType, Integer> damageStats = new HashMap<>() {{
            put(DamageType.PHYSICAL, 120);
        }};

        return ExpertiseItemTemplate.makeExpertiseAbilityItem("swordsman", "Slash", 2, "Yep.", damageStats);
    }
}