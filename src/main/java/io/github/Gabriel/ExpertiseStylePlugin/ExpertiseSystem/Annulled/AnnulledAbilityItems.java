package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Annulled;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class AnnulledAbilityItems extends AbilityItemTemplate {
    public AnnulledAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack blackHole(Skills skills) {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "Black Hole",
                new HashMap<>() {{
                    put("annulled", 30);
                }},
                "...it's a black hole. It pulls in and spaghettifies things; I don't need to spell this out for you.",
                false,
                "Area",
                15,
                8,
                30,
                50,
                List.of("§5§n3x" + "§r§5" + " Dark Damage 🌀"),
                null,
                List.of(WAND, STAFF, CATALYST),
                skills);
    }
}