package io.github.Gabriel.expertiseStylePlugin.ExpertiseStuff;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStuff.Menus.Expertise.ExpertiseMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChooseExpertiseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            new ExpertiseMenu(ExpertiseStylePlugin.getPlayerMenuUtility(player)).open();
        }

        return true;
    }
}