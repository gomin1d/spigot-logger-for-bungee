package ua.lokha.spigotloggerforbungee.paperspigotclasses;


import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.command.ConsoleCommandSender;
import net.minecrell.terminalconsole.TerminalConsoleAppender;
import org.jline.reader.*;
import org.jline.reader.LineReader.Option;
import org.jline.terminal.Terminal;
import ua.lokha.spigotloggerforbungee.SpigotLoggerForBungeePlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * this class from paperspigot: com.destroystokyo.paper.console.TerminalHandler
 */
public class TerminalHandler {
    private TerminalHandler() {
    }

    public static boolean handleCommands(BungeeCord bungeeCord, SpigotLoggerForBungeePlugin plugin) {
        Terminal terminal = TerminalConsoleAppender.getTerminal();
        System.out.println("TERTIMAL: " + terminal);
        if (terminal == null) {
            return false;
        } else {
            LineReader reader = LineReaderBuilder.builder().appName("BungeeCord").terminal(terminal)
                    .completer((reader1, line, candidates) -> {
                        final String buffer = line.line();
                        List<String> suggestions = new ArrayList<>();
                        bungeeCord.getPluginManager().dispatchCommand(bungeeCord.getConsole(), buffer, suggestions);
                        System.out.println("COMPLETE " + suggestions);
                        for (String suggestion : suggestions) {
                            candidates.add(new Candidate(suggestion));
                        }
//                            int lastSpace = buffer.lastIndexOf(32);
//                            return lastSpace == -1 ? cursor - buffer.length() : cursor - (buffer.length() - lastSpace - 1);
                    })
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
                        // this code from net.md_5.bungee.BungeeCordLauncher::main
                        if (!bungeeCord.getPluginManager().dispatchCommand(ConsoleCommandSender.getInstance(), line)) {
                            bungeeCord.getConsole().sendMessage((new ComponentBuilder("Command not found")).color(ChatColor.RED).create());
                        }
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

