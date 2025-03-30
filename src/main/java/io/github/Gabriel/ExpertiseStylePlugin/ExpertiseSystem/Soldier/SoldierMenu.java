package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus.ExpertiseConfirmMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.MenuSystem.Menu;
import io.github.Gabriel.expertiseStylePlugin.MenuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SoldierMenu extends Menu {
    public SoldierMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&c&lSwordsman Abilities");
    }

    @Override
    public int getSlots() {
        return 9 * 4;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack selected = event.getCurrentItem();

        if (selected.equals(SoldierAbilityItems.stab())) {
            new ExpertiseConfirmMenu(ExpertiseStylePlugin.getPlayerMenuUtility(player), selected, this).open();
        } else if (selected.equals(SoldierAbilityItems.slash())) {
            new ExpertiseConfirmMenu(ExpertiseStylePlugin.getPlayerMenuUtility(player), selected, this).open();
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, SoldierAbilityItems.stab());
        inventory.setItem(11, SoldierAbilityItems.slash());
    }
}
