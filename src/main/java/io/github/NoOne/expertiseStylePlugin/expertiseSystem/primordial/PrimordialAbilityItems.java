package io.github.NoOne.expertiseStylePlugin.expertiseSystem.primordial;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemMaker;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

import static io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityPrerequisite.GROUNDED;
import static io.github.NoOne.nMLItems.ItemType.*;

public class PrimordialAbilityItems extends AbilityItemManager {
    public PrimordialAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack chuckRock(Skills skills) {
        return ExpertiseAbilityItemMaker.makeExpertiseAbilityItem(
                "Chuck Rock",
                new HashMap<>() {{
                    put("primordial", 1);
                }},
                "Pick up and chuck a rock. It's not magical or anything, you just find a rock and throw it.", 
                null,
                false,
                "Single",
                20,
                0,
                5,
                10,
                List.of("§4§n1.5x§r§4 Physical Damage ⚔"),
                null,
                List.of(), skills);
    }

    public static ItemStack pumpkinBomb(Skills skills) {
        return ExpertiseAbilityItemMaker.makeExpertiseAbilityItem(
                "Pumpkin Bomb",
                new HashMap<>() {{
                    put("primordial", 15);
                    put("annulled", 5);
                }},
                "Summon and throw a pumpkin that explodes on contact. Spooky!", 
                null,
                false,
                "Area",
                20,
                0,
                15,
                30,
                List.of("§f§n100%§r§f Weapon Damage \uD83D\uDDE1", "§c§n1.5x§r§c Fire Damage 🔥", "§2§n1.5x§r§2 Earth Damage 🪨"),
                null,
                List.of(WAND, STAFF, CATALYST), skills);
    }

    public static ItemStack airBall(Skills skills) {
        return ExpertiseAbilityItemMaker.makeExpertiseAbilityItem(
                "Air Ball",
                new HashMap<>() {{
                    put("primordial", 10);
                }},
                "Dunk on your enemies with a compressed ball of air. Kobe!",
                List.of(GROUNDED),
                false,
                "Area",
                15,
                0,
                10,
                15,
                List.of("§f§n50%§r§f Weapon Damage \uD83D\uDDE1", "§7§n2.5x§r§7 Air Damage ☁"),
                null,
                List.of(), skills);
    }
}