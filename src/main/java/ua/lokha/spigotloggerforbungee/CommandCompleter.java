package ua.lokha.spigotloggerforbungee;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Command;
import org.apache.commons.lang3.StringUtils;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import ua.lokha.spigotloggerforbungee.utils.MyObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Completer command
 */
public class CommandCompleter implements Completer {
    private final Pattern argsSplit = Pattern.compile(" ");
    private final Map<String, Command> commandMap;
    private BungeeCord bungeeCord;

    public CommandCompleter(BungeeCord bungeeCord) {
        this.bungeeCord = bungeeCord;
        this.commandMap = MyObject.wrap(bungeeCord.getPluginManager()).getField("commandMap").getObject();
    }

    @Override
    public void complete(LineReader reader1, ParsedLine line, List<Candidate> candidates) {
        final String buffer = line.line();
        List<String> suggestions = new ArrayList<>();
        if (!this.suggestCommands(line, candidates)) {
            bungeeCord.getPluginManager().dispatchCommand(bungeeCord.getConsole(), buffer, suggestions);
            for (String suggestion : suggestions) {
                candidates.add(new Candidate(suggestion));
            }
        }
//                            int lastSpace = buffer.lastIndexOf(32);
//                            return lastSpace == -1 ? cursor - buffer.length() : cursor - (buffer.length() - lastSpace - 1);
    }

    private boolean suggestCommands(ParsedLine line, List<Candidate> candidates) {
        final String buffer = line.line();
        String[] split = argsSplit.split(buffer, -1);
        if (split.length == 1) {
            final Command command = commandMap.get(split[0]);
            if (command == null) {
                commandMap.keySet().stream()
                        .filter(s -> StringUtils.startsWithIgnoreCase(s, buffer))
                        .forEach(s->candidates.add(new Candidate(s)));
                return true;
            }
        }
        return false;
    }
}
