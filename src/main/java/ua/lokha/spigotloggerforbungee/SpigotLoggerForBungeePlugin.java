package ua.lokha.spigotloggerforbungee;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.log.LogDispatcher;
import ua.lokha.spigotloggerforbungee.injectclasses.InjectConsoleReader;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.command.ConsoleCommandSender;
import org.fusesource.jansi.AnsiConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.io.IoBuilder;
import ua.lokha.spigotloggerforbungee.paperspigotclasses.ForwardLogHandler;
import ua.lokha.spigotloggerforbungee.paperspigotclasses.TerminalHandler;
import ua.lokha.spigotloggerforbungee.utils.MyObject;
import ua.lokha.spigotloggerforbungee.utils.Try;

/**
 * many fragments of this class are taken from net.minecraft.server.v1_12_R1.DedicatedServer::init
 */
public class SpigotLoggerForBungeePlugin extends Plugin {

    private org.apache.logging.log4j.Logger logger;
    private ExecutorService commandExecutor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("Console Thread #%d").build());

    @Override
    public void onLoad() {
        BungeeCord bungeeCord = BungeeCord.getInstance();
        // undo ansi library
        System.setOut(AnsiConsole.system_out);
        System.setErr(AnsiConsole.system_err);

        //init logger from log4j2.xml from resources this plugin
        this.logger = LogManager.getLogger();

        //set default I/O
        org.apache.logging.log4j.Logger logger = LogManager.getRootLogger();
        System.setOut(IoBuilder.forLogger(logger).setLevel(Level.INFO).buildPrintStream());
        System.setErr(IoBuilder.forLogger(logger).setLevel(Level.WARN).buildPrintStream());

        this.getLogger().info("Goodbye, old logger (All subsequent logs will be posted in logs/latest.log)."); // :D

        // fix all loggers
        this.fixLoggers(bungeeCord);

        this.getLogger().info("Hello, new logger!");
    }

    @Override
    public void onEnable() {
        BungeeCord bungeeCord = BungeeCord.getInstance();

        // console thread
        this.startConsoleThread(bungeeCord);

        //disable default console
        this.disableDefaultConsole(bungeeCord);
    }

    @Override
    public void onDisable() {

    }

    private void fixLoggers(BungeeCord bungeeCord) {
        java.util.logging.Logger global = java.util.logging.Logger.getLogger("");
        this.fixLogger(global);

        this.fixLogger(bungeeCord.getLogger());

        // stop log thread
        Try.ignore(()->MyObject.wrap(bungeeCord.getLogger()).getField("dispatcher").getObject(LogDispatcher.class).interrupt());

        final Logger newLogger = Logger.getLogger("BungeeCord");
        newLogger.setLevel(java.util.logging.Level.ALL);
        MyObject.wrap(bungeeCord).setField("logger", newLogger);

        bungeeCord.getPluginManager().getPlugins().forEach(plugin -> plugin.getLogger().setParent(newLogger));
    }

    public boolean isRunning() {
        return BungeeCord.getInstance().isRunning;
    }

    private void disableDefaultConsole(BungeeCord bungeeCord) {
        // so that the while does not work in from net.md_5.bungee.BungeeCordLauncher::main
        Try.unchecked(() -> MyObject.wrap(bungeeCord).setField("consoleReader", new InjectConsoleReader()));
    }

    private void fixLogger(Logger logger) {
        logger.setUseParentHandlers(false);
        for (Handler handler : logger.getHandlers()) {
            logger.removeHandler(handler);
        }
        final ForwardLogHandler handler = new ForwardLogHandler();
        logger.addHandler(handler);
    }

    private void startConsoleThread(BungeeCord bungeeCord) {

        Thread thread = new Thread(() -> {
            if (!TerminalHandler.handleCommands(bungeeCord, this)) {
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

                try {
                    // this code from net.md_5.bungee.BungeeCordLauncher::main
                    while(this.isRunning()) {
                        String line = bufferedreader.readLine();
                        if (line != null && line.trim().length() > 0) {
                            this.dispatchCommand(line);
                        }
                    }
                } catch (IOException var4) {
                    logger.error("Exception handling console input", var4);
                }
            }
        }, "Server console handler");

        thread.setDaemon(true);
        thread.start();
    }

    public void dispatchCommand(String line) {
        commandExecutor.submit(()->{
            final BungeeCord bungeeCord = BungeeCord.getInstance();
            if (!bungeeCord.getPluginManager().dispatchCommand(ConsoleCommandSender.getInstance(), line)) {
                bungeeCord.getConsole().sendMessage((new ComponentBuilder("Command not found")).color(ChatColor.RED).create());
            }
        });
    }
}
