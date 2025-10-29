package io.github.NoOne.expertiseStylePlugin.expertiseSystem.soldier;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class SoldierAbilityItems extends AbilityItemTemplate {
    public SoldierAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack slash(Skills skills) {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "Slash",
                new HashMap<>() {{
                    put("soldier", 1);
                }},
                "Yep.",
                false,
                "Area",
                2,
                0,
                2,
                15,
                List.of("§f§n120%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(SWORD, AXE, SPEAR),
                skills);
    }
}