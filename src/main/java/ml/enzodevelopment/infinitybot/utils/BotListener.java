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
        if (event.getChannel().getId().equals("471406980137746442")) {
            if (event.getMessageId().equals("471661254746963988")) {
                if (event.getReactionEmote().getName().equals("\u2714")) {
                    if (!event.getMember().getRoles().contains(event.getGuild().getRoleById("471400678154043412"))) {
                        event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getGuild().getRoleById("471400678154043412")).queue();
                    }
                } else {
                    event.getReaction().removeReaction().queue();
                }
            } else if (event.getMessageId().equals("471668210467471381")) {
                if (event.getReactionEmote().isEmote()) {
                    switch (event.getReactionEmote().getEmote().getName()) {
                        case "copper":
                            if (!event.getMember().getRoles().contains(event.getGuild().getRoleById("471403052591611915"))) {
                                event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getGuild().getRoleById("471403052591611915")).queue();
                            }
                            event.getReaction().removeReaction().queue();
                            break;
                        case "bronze":
                            if (!event.getMember().getRoles().contains(event.getGuild().getRoleById("471403030647013376"))) {
                                event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getGuild().getRoleById("471403030647013376")).queue();
                            }
                            event.getReaction().removeReaction().queue();
                            break;
                        case "silver":
                            if (!event.getMember().getRoles().contains(event.getGuild().getRoleById("471403207760019476"))) {
                                event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getGuild().getRoleById("471403207760019476")).queue();
                            }
                            event.getReaction().removeReaction().queue();
                            break;
                        case "gold":
                            if (!event.getMember().getRoles().contains(event.getGuild().getRoleById("471403067552563233"))) {
                                event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getGuild().getRoleById("471403067552563233")).queue();
                            }
                            event.getReaction().removeReaction().queue();
                            break;
                        case "platinum":
                            if (!event.getMember().getRoles().contains(event.getGuild().getRoleById("471403080844312587"))) {
                                event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getGuild().getRoleById("471403080844312587")).queue();
                            }
                            event.getReaction().removeReaction().queue();
                            break;
                        case "diamond":
                            if (!event.getMember().getRoles().contains(event.getGuild().getRoleById("471403115611029514"))) {
                                event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getGuild().getRoleById("471403115611029514")).queue();
                            }
                            event.getReaction().removeReaction().queue();
                        default:
                            event.getReaction().removeReaction().queue();
                            break;
                    }
                } else {
                    event.getReaction().removeReaction().queue();
                }
            }
        }
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
