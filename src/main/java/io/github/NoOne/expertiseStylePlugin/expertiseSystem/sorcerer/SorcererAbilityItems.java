package io.github.NoOne.expertiseStylePlugin.expertiseSystem.sorcerer;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class SorcererAbilityItems extends AbilityItemTemplate {
    public SorcererAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack magicMissileEX(Skills skills) {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "Magic Missile EX",
                new HashMap<>() {{
                    put("sorcerer", 1);
                }},
                "Shoot your basic magic attack 5 times",
                false,
                "Single",
                16,
                0,
                5,
                20,
                List.of("§f§n50%" + "§r§f" + " Weapon Damage \uD83D\uDDE1 §7§o(per missile)"),
                null,
                List.of(WAND, STAFF, CATALYST),
                skills);
    }
}