package io.github.NoOne.expertiseStylePlugin.expertiseSystem.hallowed;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class HallowedAbilityItems extends AbilityItemTemplate {
    public HallowedAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack halo(Skills skills) {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "Halo",
                new HashMap<>() {{
                    put("hallowed", 15);
                }},
                "Throw a ring of radiant energy that rebounds back to you, damaging anyone touching it",
                false,
                "Area",
                20,
                0,
                10,
                25,
                List.of("§f§n15%§r§f Weapon Damage \uD83D\uDDE1", "§f§n.35x§r§f Radiant Damage ✦"),
                null,
                List.of(WAND, STAFF, CATALYST),
                skills);
    }
}