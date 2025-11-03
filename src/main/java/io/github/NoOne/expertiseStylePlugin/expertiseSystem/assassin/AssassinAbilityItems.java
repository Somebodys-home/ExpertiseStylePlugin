package io.github.NoOne.expertiseStylePlugin.expertiseSystem.assassin;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemCreator;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class AssassinAbilityItems extends AbilityItemManager {
    public AssassinAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack slashandDash(Skills skills) {
        return ExpertiseAbilityItemCreator.makeExpertiseAbilityItem(
                "Slash & Dash",
                new HashMap<>() {{
                    put("assassin", 1);
                }},
                "Dash forwards, dealing damage to anyone in your way", 
                null,
                false,
                "Area",
                10,
                0,
                5,
                20,
                List.of("§f§n150%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(DAGGER), skills);
    }
}