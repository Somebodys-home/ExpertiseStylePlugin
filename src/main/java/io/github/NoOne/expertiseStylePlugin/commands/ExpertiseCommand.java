package io.github.NoOne.expertiseStylePlugin.commands;

import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.expertiseMenus.ExpertiseMenu;
import io.github.NoOne.menuSystem.MenuSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpertiseCommand implements CommandExecutor {
    private ExpertiseStylePlugin expertiseStylePlugin;

    public ExpertiseCommand(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            new ExpertiseMenu(expertiseStylePlugin, MenuSystem.getPlayerMenuUtility(player)).open();
        }
        return true;
    }
}