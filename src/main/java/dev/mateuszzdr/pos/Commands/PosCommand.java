package dev.mateuszzdr.pos.Commands;

import dev.mateuszzdr.pos.Pos;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PosCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Pos.GetInstance().getCords();
        String Loc = "";
        if(Pos.PlayerCords.containsKey(strings[0])) {
            if(Pos.PlayerCords.containsKey(strings[0])) {
                Loc = Math.round(Pos.PlayerCords.get(strings[0]).x()) + ", " + Math.round(Pos.PlayerCords.get(strings[0]).y()) + ", " + Math.round(Pos.PlayerCords.get(strings[0]).z()) + ", " + Pos.PlayerCords.get(strings[0]).getWorld().getName();
            }
            Player p = Bukkit.getPlayer(strings[0]);
            if(p != null && p.isOnline()) {
                commandSender.sendMessage("§2§lplayer " + strings[0] + " is at " + "§r§3" + Loc);
            } else {
                commandSender.sendMessage("§2§llast known position of " + strings[0] + " is " + "§r§3" + Loc);

            }
            return true;
        }
        commandSender.sendMessage("§c§lsomething went wrong");
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
