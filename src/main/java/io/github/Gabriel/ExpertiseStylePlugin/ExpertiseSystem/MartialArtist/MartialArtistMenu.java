package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.MartialArtist;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus.ExpertiseConfirmMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus.ExpertiseMenu;
import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MartialArtistMenu extends Menu {
    private ExpertiseStylePlugin expertiseStylePlugin;

    public MartialArtistMenu(ExpertiseStylePlugin expertiseStylePlugin, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.expertiseStylePlugin = expertiseStylePlugin;
    }

    @Override
    public String getMenuName() {
        return "§4§lMartial Artist Abilities";
    }

    @Override
    public int getSlots() {
        return 9 * 4;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        ItemStack selected = event.getCurrentItem();
        assert selected != null;

        if (event.getSlot() == 35) {
            new ExpertiseMenu(expertiseStylePlugin, playerMenuUtility).open();
        } else {
            new ExpertiseConfirmMenu(expertiseStylePlugin, playerMenuUtility, selected, this).open();
        }
    }

    @Override
    public void handlePlayerMenu(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, MartialArtistAbilityItems.tenHitCombo());

        // Backout button
        ItemStack nvm = new ItemStack(Material.BARRIER);
        ItemMeta meta = nvm.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&l<= Go back"));
        nvm.setItemMeta(meta);

        inventory.setItem(35, nvm);
    }
}
