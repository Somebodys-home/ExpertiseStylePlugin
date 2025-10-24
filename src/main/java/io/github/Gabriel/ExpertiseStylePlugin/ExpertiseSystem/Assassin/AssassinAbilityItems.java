package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class AssassinAbilityItems extends AbilityItemTemplate {
    public AssassinAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack slashandDash(Skills skills) {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "Slash & Dash",
                new HashMap<>() {{
                    put("assassin", 1);
                }},
                "Dash forwards, dealing damage to anyone in your way",
                false,
                "Area",
                10,
                0,
                5,
                20,
                List.of("§f§n150%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(DAGGER),
                skills);
    }
}