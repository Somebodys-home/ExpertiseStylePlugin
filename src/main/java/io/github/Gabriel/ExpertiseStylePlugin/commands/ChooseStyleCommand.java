package io.github.Gabriel.expertiseStylePlugin.commands;

import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleMenus.StyleMenu;
import io.github.Gabriel.menuSystem.MenuSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChooseStyleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            new StyleMenu(MenuSystem.getPlayerMenuUtility(player)).open();
        }

        return true;
    }
}