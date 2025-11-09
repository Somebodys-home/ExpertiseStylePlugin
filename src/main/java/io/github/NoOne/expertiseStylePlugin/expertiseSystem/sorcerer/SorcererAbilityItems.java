package io.github.NoOne.expertiseStylePlugin.expertiseSystem.sorcerer;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemCreator;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class SorcererAbilityItems extends AbilityItemManager {
    public SorcererAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack magicMissileEX(Skills skills) {
        return ExpertiseAbilityItemCreator.makeExpertiseAbilityItem(
                "Magic Missile EX",
                new HashMap<>() {{
                    put("sorcerer", 1);
                }},
                "Shoot your basic magic attack 5 times", 
                null,
                false,
                "Single",
                16,
                0,
                8,
                15,
                List.of("§f§n50%" + "§r§f" + " Weapon Damage \uD83D\uDDE1 §7§o(per missile)"),
                null,
                List.of(WAND, STAFF, CATALYST), skills);
    }

    public static ItemStack dragonsBreath(Skills skills) {
        return ExpertiseAbilityItemCreator.makeExpertiseAbilityItem(
                "Dragon's Breath",
                new HashMap<>() {{
                    put("sorcerer", 25);
                }},
                "Spew a cone of flames, kinda like you ate some really spicy curry",
                null,
                false,
                "Area",
                12,
                5,
                20,
                25,
                List.of("§c§n1x§r§c Fire Damage 🔥 §7§o(every 1s)"),
                null,
                List.of(WAND, STAFF), skills);
    }
}