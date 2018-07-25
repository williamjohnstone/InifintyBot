package ml.enzodevelopment.infinitybot;

import io.sentry.Sentry;
import ml.enzodevelopment.infinitybot.commands.basic.PingCommand;
import ml.enzodevelopment.infinitybot.commands.owner.EvalCommand;
import ml.enzodevelopment.infinitybot.utils.BotListener;
import ml.enzodevelopment.infinitybot.utils.Config;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;
import net.dv8tion.jda.core.requests.RestAction;
import net.dv8tion.jda.core.utils.JDALogger;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class InfinityBot {

    public static List<Command> cmdList = new ArrayList<>();

    public static void main(String[] args) {
        RestAction.DEFAULT_FAILURE = t ->
        {
            Logger LOG = JDALogger.getLog(RestAction.class);
            if (t instanceof ErrorResponseException && ((ErrorResponseException) t).getErrorCode() != 10008) {
                if (LOG.isDebugEnabled()) {
                    LOG.error("RestAction queue returned failure", t);
                } else {
                    LOG.error("RestAction queue returned failure: [{}] {}", t.getClass().getSimpleName(), t.getMessage());
                }
            }
        };

        Config config = new Config();
        config.loadConfig();
        Sentry.init(Config.sentry_dsn);
        JDABuilder builder = new JDABuilder(AccountType.BOT)
                .addEventListener(new BotListener())
                .setToken(Config.Discord_Token)
                .setAutoReconnect(true)
                .setStatus(OnlineStatus.DO_NOT_DISTURB);

        try {
            cmdList.add(new PingCommand());
            cmdList.add(new EvalCommand());
            JDA jda = builder.buildBlocking();
            jda.getPresence().setStatus(OnlineStatus.ONLINE);
            jda.getPresence().setGame(Game.watching(jda.getGuildCache().size() + " servers! | !help"));
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
