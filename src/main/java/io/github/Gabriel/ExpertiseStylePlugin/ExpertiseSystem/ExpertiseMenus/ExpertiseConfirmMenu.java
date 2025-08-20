package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus;


import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveSelectedAbilitiesSystem.SelectedManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ExpertiseConfirmMenu extends Menu {
    private final ItemStack i3;
    private final ItemStack i4;
    private final ItemStack selected;
    private final Menu previous;
    private SelectedManager selectedManager;

    public ExpertiseConfirmMenu(PlayerMenuUtility playerMenuUtility, ItemStack selected, Menu previous) {
        super(playerMenuUtility);
        this.i3 = playerMenuUtility.getOwner().getInventory().getItem(2);
        this.i4 = playerMenuUtility.getOwner().getInventory().getItem(3);
        this.selected = selected;
        this.previous = previous;
        selectedManager = new SelectedManager(JavaPlugin.getPlugin(ExpertiseStylePlugin.class));
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&6&lYou sure?");
    }

    @Override
    public int getSlots() {
        return 9 * 3;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        switch (slot) {
            case 11 -> player.getInventory().setItem(2, selected);
            case 15 -> player.getInventory().setItem(3, selected);
            case 22 -> previous.open();
        }

        previous.open();
    }

    @Override
    public void handlePlayerMenu(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(4, selected);
        inventory.setItem(11, i3);
        inventory.setItem(15, i4);

        // Backout button
        ItemStack nvm = new ItemStack(Material.RED_DYE);
        ItemMeta meta = nvm.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&l<= Go back"));
        nvm.setItemMeta(meta);

        inventory.setItem(22, nvm);
    }
}
