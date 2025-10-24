package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.MartialArtist;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class MartialArtistAbilityItems extends AbilityItemTemplate {
    public MartialArtistAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack tenHitCombo(Skills skills) {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "10-Hit Combo",
                new HashMap<>() {{
                    put("martialartist", 30);
                }},
                "Perform a devastating 10 hit combo, ending with an uppercut, so long as you can land every hit. (Energy cost is per hit)",
                false,
                "Single",
                5,
                0,
                15,
                10,
                List.of("§f§n300%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(GLOVE),
                skills);
    }
}