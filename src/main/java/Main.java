import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBot bot = new TelegramBot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
            SwingUtilities.invokeLater(() -> {Frame window = new Frame(bot);});
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
