package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Hallowed;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class HallowedAbilityItems extends AbilityItemTemplate {
    public HallowedAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack halo() {
        return ExpertiseItemTemplate.makeExpertiseAbilityItem(
                "hallowed",
                "Halo",
                "Throw a ring of radiant energy that rebounds back to you, damaging anyone touching it",
                false,
                "Area",
                20,
                0,
                10,
                25,
                List.of("§f§n50%" + "§r§f" + " Light Damage ✦"),
                null,
                List.of(WAND, STAFF, CATALYST));
    }
}