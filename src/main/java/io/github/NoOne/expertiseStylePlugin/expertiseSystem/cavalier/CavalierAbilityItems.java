package io.github.NoOne.expertiseStylePlugin.expertiseSystem.cavalier;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemCreator;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

import static io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityPrerequisite.*;
import static io.github.NoOne.nMLItems.ItemType.*;

public class CavalierAbilityItems extends AbilityItemManager {
    public CavalierAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack seismicSlam(Skills skills) {
        return ExpertiseAbilityItemCreator.makeExpertiseAbilityItem(
                "Seismic Slam",
                new HashMap<>() {{
                    put("cavalier", 10);
                }},
                "Jump into the air, then crash into the ground, launching anyone nearby away from you", 
                List.of(GROUNDED),
                false,
                "Area",
                16,
                0,
                15,
                30,
                List.of("§f§n250%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(SPEAR, HAMMER), skills);
    }
}