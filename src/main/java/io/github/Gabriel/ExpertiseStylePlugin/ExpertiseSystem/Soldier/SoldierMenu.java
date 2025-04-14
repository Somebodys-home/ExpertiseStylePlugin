package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus.ExpertiseConfirmMenu;
import io.github.Gabriel.menuSystem.Menu;
import io.github.Gabriel.menuSystem.MenuSystem;
import io.github.Gabriel.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SoldierMenu extends Menu {
    private ExpertiseStylePlugin expertiseStylePlugin;

    public SoldierMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        expertiseStylePlugin = ExpertiseStylePlugin.getInstance();
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

        assert selected != null;
        new ExpertiseConfirmMenu(MenuSystem.getPlayerMenuUtility(player), selected, this).open();
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, SoldierAbilityItems.slash());
    }
}
