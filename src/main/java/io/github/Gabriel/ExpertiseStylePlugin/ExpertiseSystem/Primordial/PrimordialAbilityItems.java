package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Primordial;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseAbilityItemTemplate;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class PrimordialAbilityItems extends AbilityItemTemplate {
    public PrimordialAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack chuckRock() {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "primordial",
                "Chuck Rock",
                "Pick up and chuck a rock. It's not magical or anything, you just find a rock and throw it.",
                false,
                "Single",
                20,
                0,
                5,
                10,
                List.of("§4§n150%" + "§r§4" + " Physical Damage ⚔"),
                null,
                List.of());
    }
}