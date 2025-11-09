package io.github.NoOne.expertiseStylePlugin.expertiseSystem.annulled;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemCreator;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class AnnulledAbilityItems extends AbilityItemManager {
    public AnnulledAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack blackHole(Skills skills) {
        return ExpertiseAbilityItemCreator.makeExpertiseAbilityItem(
                "Black Hole",
                new HashMap<>() {{
                    put("annulled", 50);
                }},
                "...it's a black hole. It pulls in and spaghettifies things; I don't need to spell this out for you.",
                null,
                false,
                "Area",
                15,
                8,
                30,
                50,
                List.of("§5§n5x" + "§r§5" + " Dark Damage 🌀"),
                null,
                List.of(WAND, STAFF, CATALYST), skills);
    }
}