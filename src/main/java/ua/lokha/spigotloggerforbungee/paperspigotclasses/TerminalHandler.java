package ua.lokha.spigotloggerforbungee.paperspigotclasses;


import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.command.ConsoleCommandSender;
import net.minecrell.terminalconsole.TerminalConsoleAppender;
import org.jline.reader.*;
import org.jline.reader.LineReader.Option;
import org.jline.terminal.Terminal;
import ua.lokha.spigotloggerforbungee.CommandCompleter;
import ua.lokha.spigotloggerforbungee.SpigotLoggerForBungeePlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * this class from paperspigot: com.destroystokyo.paper.console.TerminalHandler
 */
public class TerminalHandler {
    private TerminalHandler() {
    }

    public static boolean handleCommands(BungeeCord bungeeCord, SpigotLoggerForBungeePlugin plugin) {
        Terminal terminal = TerminalConsoleAppender.getTerminal();
        if (terminal == null) {
            return false;
        } else {
            LineReader reader = LineReaderBuilder.builder().appName("BungeeCord").terminal(terminal)
                    .completer(new CommandCompleter(bungeeCord))
                    .build();
            reader.unsetOpt(Option.INSERT_TAB);
            TerminalConsoleAppender.setReader(reader);

            try {
                while (plugin.isRunning()) {
                    String line;
                    try {
                        line = reader.readLine("> ");
                    } catch (EndOfFileException var9) {
                        continue;
                    }

                    if (line == null) {
                        break;
                    }

                    line = line.trim();
                    if (!line.isEmpty()) {
                        plugin.dispatchCommand(line);
                    }
                }
            } catch (UserInterruptException var10) {
                bungeeCord.stop(var10.getClass().getName() + ": " + var10.getMessage());
            } finally {
                TerminalConsoleAppender.setReader(null);
            }

            return true;
        }
    }
}

