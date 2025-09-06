import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.Set;

public class TelegramBot extends TelegramLongPollingBot {
    private Set<Subscriber> subscribers = new HashSet<>();

    // Constructor......................................................................................................
    public TelegramBot() {

    }

    // toString.........................................................................................................

    // Methods..........................................................................................................
    @Override
    public void onUpdateReceived(Update update) {
        Subscriber subscriber = new Subscriber(update.getMessage().getChatId());
        this.subscribers.add(subscriber);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Welcome aboard the poll bot.");
        sendMessage.setChatId(update.getMessage().getChatId());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "Tomer_Poll_Bot";
    }

    public String getBotToken() {
        return "8260282863:AAE9CpomVzWNTKY-U7cnJjh5mNJddsHdH5w";
    }

    // Getters & Setters................................................................................................
    public Set<Subscriber> getSubscribers() {
        return subscribers;
    }
}
