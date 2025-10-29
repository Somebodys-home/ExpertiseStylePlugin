package io.github.NoOne.expertiseStylePlugin.expertiseSystem.marksman;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class MarksmanAbilityItems extends AbilityItemTemplate {
    public MarksmanAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack rapidShot(Skills skills) {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "Rapid Shot",
                new HashMap<>() {{
                    put("marksman", 20);
                }},
                "Nock an arrow for every .5 second this ability is toggled on. After untoggling, or nocking 10 arrows, shoot all prepared arrows rapidly",
                true,
                "Single",
                70,
                0,
                10,
                25,
                List.of("§f§n50%" + "§r§f" + " Weapon Damage \uD83D\uDDE1 §7§o(per arrow)"),
                null,
                List.of(BOW),
                skills);
    }
}