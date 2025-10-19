package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Hallowed;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseAbilityItemTemplate;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class HallowedAbilityItems extends AbilityItemTemplate {
    public HallowedAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack halo() {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "hallowed",
                "Halo",
                "Throw a ring of radiant energy that rebounds back to you, damaging anyone touching it",
                false,
                "Area",
                20,
                0,
                10,
                25,
                List.of("§f§n35%" + "§r§f" + " Radiant Damage ✦", "§f§n15%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(WAND, STAFF, CATALYST));
    }
}