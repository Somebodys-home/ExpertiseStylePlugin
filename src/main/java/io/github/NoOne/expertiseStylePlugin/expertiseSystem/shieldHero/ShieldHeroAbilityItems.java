package io.github.NoOne.expertiseStylePlugin.expertiseSystem.shieldHero;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemCreator;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class ShieldHeroAbilityItems extends AbilityItemManager {
    public ShieldHeroAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack secondWind(Skills skills) {
        return ExpertiseAbilityItemCreator.makeExpertiseAbilityItem(
                "Second Wind",
                new HashMap<>() {{
                    put("shieldhero", 1);
                }},
                "Take a moment to steel your resolve to fully regain your guard", 
                null,
                false,
                "Self",
                0,
                0,
                20,
                10,
                null,
                List.of("§fRestore your §nGuard§r§f ⛨"),
                List.of(SHIELD), skills);
    }
}