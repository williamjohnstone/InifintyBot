package ml.enzodevelopment.infinitybot.utils;

import ml.enzodevelopment.infinitybot.Command;
import ml.enzodevelopment.infinitybot.InfinityBot;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotListener extends ListenerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private GuildConfig guildConfig = new GuildConfig();

    public static Command getCommand(String alias) {
        for (Command command : InfinityBot.cmdList) {
            for (String commandAlias : command.getAliases()) {
                if (commandAlias.equals(alias)) {
                    return command;
                }
            }
        }
        return null;
    }

    @Override
    public void onReady(ReadyEvent event) {
        logger.info("InfinityBot is running! Bot should be online.");
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String botPrefix = guildConfig.getPrefix(event.getGuild().getId());

        String substringMessage = "";
        String msg = event.getMessage().getContentRaw().toLowerCase();
        String args[] = event.getMessage().getContentRaw().split("\\s+");
        if (msg.startsWith(botPrefix)) {
            substringMessage = msg.substring(botPrefix.length());
        }
        String[] parts = substringMessage.split("\\s+");
        Command cmd = getCommand(parts[0]);

        boolean checks = runChecks(event, botPrefix);
        if (checks && cmd != null) {
            cmd.execute(args, event);
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

    }

    private boolean runChecks(GuildMessageReceivedEvent event, String botPrefix) {
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_WRITE)) {
            return false;
        } else if (!event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
            return false;
        }
        boolean startsWithPrefix = event.getMessage().getContentRaw().startsWith(botPrefix);
        boolean notBot = !event.getMessage().getAuthor().isBot();

        return startsWithPrefix && notBot;

    }

}
