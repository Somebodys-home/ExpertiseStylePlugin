package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ShieldHero;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseAbilityItemTemplate;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class ShieldHeroAbilityItems extends AbilityItemTemplate {
    public ShieldHeroAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack secondWind() {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "shield",
                "Second Wind",
                "Take some time to steel your resolve, fully regaining your guard",
                false,
                "Self",
                0,
                0,
                10,
                20,
                null,
                List.of("§fRestore your §nGuard§r§f ⛨"),
                List.of(SHIELD));
    }
}