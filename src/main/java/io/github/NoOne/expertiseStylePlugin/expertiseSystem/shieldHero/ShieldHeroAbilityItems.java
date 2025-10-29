package io.github.NoOne.expertiseStylePlugin.expertiseSystem.shieldHero;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class ShieldHeroAbilityItems extends AbilityItemTemplate {
    public ShieldHeroAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack secondWind(Skills skills) {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "Second Wind",
                new HashMap<>() {{
                    put("shieldhero", 1);
                }},
                "Take a moment to steel your resolve to fully regain your guard",
                false,
                "Self",
                0,
                0,
                20,
                10,
                null,
                List.of("§fRestore your §nGuard§r§f ⛨"),
                List.of(SHIELD),
                skills);
    }
}