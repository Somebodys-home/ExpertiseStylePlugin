package io.github.NoOne.expertiseStylePlugin.expertiseSystem.martialArtist;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemCreator;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class MartialArtistAbilityItems extends AbilityItemManager {
    public MartialArtistAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack tenHitCombo(Skills skills) {
        return ExpertiseAbilityItemCreator.makeExpertiseAbilityItem(
                "10-Hit Combo",
                new HashMap<>() {{
                    put("martialartist", 30);
                }},
                "Perform a devastating 10 hit combo, ending with an uppercut, so long as you can land every hit. (Energy cost is per hit)", 
                null,
                false,
                "Single",
                5,
                0,
                15,
                10,
                List.of("§f§n300%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(GLOVE), skills);
    }
}