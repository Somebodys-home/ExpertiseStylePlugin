package io.github.NoOne.expertiseStylePlugin.expertiseSystem.primordial;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

import static io.github.NoOne.nMLItems.ItemType.*;

public class PrimordialAbilityItems extends AbilityItemTemplate {
    public PrimordialAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack chuckRock(Skills skills) {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "Chuck Rock",
                new HashMap<>() {{
                    put("primordial", 1);
                }},
                "Pick up and chuck a rock. It's not magical or anything, you just find a rock and throw it.",
                false,
                "Single",
                20,
                0,
                5,
                10,
                List.of("§4§n1.5x§r§4 Physical Damage ⚔"),
                null,
                List.of(),
                skills);
    }

    public static ItemStack pumpkinBomb(Skills skills) {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "Pumpkin Bomb",
                new HashMap<>() {{
                    put("primordial", 15);
                    put("annulled", 5);
                }},
                "Summon and throw a pumpkin that explodes on contact. Spooky!",
                false,
                "Area",
                20,
                0,
                15,
                30,
                List.of("§f§n50%§r§f Weapon Damage \uD83D\uDDE1", "§c§n1.5x§r§c Fire Damage 🔥", "§2§n1.5x§r§2 Earth Damage 🪨"),
                null,
                List.of(WAND, STAFF, CATALYST),
                skills);
    }
}