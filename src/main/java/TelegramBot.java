import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollAnswer;
import org.telegram.telegrambots.meta.api.objects.polls.PollOption;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Message;


import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class TelegramBot extends TelegramLongPollingBot {
    private final Set<Long> subscribers = new HashSet<>();
    private Map<String, String> pollIdsMap = new HashMap<>();


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
                Message sentMessage = execute(sendPoll);
                String pollId = sentMessage.getPoll().getId();
                System.out.println("pollId: " + pollId);
                pollIdsMap.put(question, pollId);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void onPollClosed(Poll poll) {
        String pollId = poll.getId();
        String question = poll.getQuestion();              // טקסט השאלה (אם צריך להציג)
        Integer totalVoters = poll.getTotalVoterCount();   // סה"כ מצביעים

        System.out.println("==== Poll closed ====");
        System.out.println("poll_id: " + pollId);
        System.out.println("Question: " + question);
        System.out.println("Total voters: " + totalVoters);

        // הצגת כל האופציות עם מספר מצביעים ואחוזים
        for (PollOption option : poll.getOptions()) {
            String text = option.getText();
            Integer count = option.getVoterCount();
            double percent = (totalVoters != null && totalVoters > 0)
                    ? (count * 100.0 / totalVoters)
                    : 0.0;

            System.out.printf("- %s: %d מצביעים (%.1f%%)%n", text, count, percent);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
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
            } else if (update.hasPoll()) {
                Poll poll = update.getPoll();
                Boolean isClosed = poll.getIsClosed(); // לעתים המחלקה מציעה גם isClosed()
                if (Boolean.TRUE.equals(isClosed)) {
                    onPollClosed(poll);
                }
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
