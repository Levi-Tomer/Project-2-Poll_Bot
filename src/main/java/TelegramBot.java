import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TelegramBot extends TelegramLongPollingBot {
    private final Set<Long> subscribers = new HashSet<>();

    // Constructor......................................................................................................
    public TelegramBot() {

    }

    // toString.........................................................................................................

    // Methods..........................................................................................................
    public void sendPoll(String question, List<String> options) {
        System.out.println("Reached the sendPoll method.");
        System.out.println("Current number of subscribers: " + this.subscribers.size());
        SendPoll sendPoll = new SendPoll();
        sendPoll.setQuestion(question);
        sendPoll.setOptions(options);
        sendPoll.setIsAnonymous(true);
        sendPoll.setAllowMultipleAnswers(false);
        sendPoll.setOpenPeriod(60 * 5);
        for (Long subscriber : this.subscribers) {
            sendPoll.setChatId(String.valueOf(subscriber));
            System.out.println("About to send poll to chatId = " + subscriber); // test
            try {
                execute(sendPoll);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            long newPossibleSubId = update.getMessage().getChatId();
            String newPossibleSubText = update.getMessage().getText();
            if (this.subscribers.contains(newPossibleSubId)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("You are already signed for the service.\nI'm a bot, but unlike GPT I have nothing to say.\nSo just wait for the next poll.");
                sendMessage.setChatId(newPossibleSubId);
                execute(sendMessage);
            } else if (newPossibleSubText.equals("/start") || newPossibleSubText.equals("Hi") || newPossibleSubText.equals("היי")) {
                SendMessage sendMessage = new SendMessage();
                this.subscribers.add(newPossibleSubId);
                sendMessage.setText("Hi! Welcome to the poll bot.\nFrom now on you will be able to answer our polls.");
                sendMessage.setChatId(newPossibleSubId);
                execute(sendMessage);
                for (Long subscriber : this.subscribers) {
                    if (!(subscriber.equals(newPossibleSubId))) {
                        sendMessage.setText("A new member just joined the poll cult. Please welcome " +
                                update.getMessage().getFrom().getFirstName() +
                                ".\nCurrent number of subscribers is " + this.subscribers.size());
                        sendMessage.setChatId(String.valueOf(subscriber));
                        execute(sendMessage);
                    }
                }
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("If you would like to sign up for the poll service, please send a returning message with one of the words:\n" +
                        "/start\n" + "Hi\n" + "היי");
                sendMessage.setChatId(newPossibleSubId);
                execute(sendMessage);
            }
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
    public Set<Long> getSubscribers() {
        return subscribers;
    }
}
