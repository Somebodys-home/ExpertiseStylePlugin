package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseAbilityItemTemplate;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class SoldierAbilityItems extends AbilityItemTemplate {
    public SoldierAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack slash() {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "swordsman",
                "Slash",
                "Yep.",
                false,
                "Area",
                2,
                0,
                2,
                15,
                List.of("§f§n120%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(SWORD, AXE));
    }
}