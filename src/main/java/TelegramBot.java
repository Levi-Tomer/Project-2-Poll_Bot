import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollOption;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.polls.StopPoll;


import java.util.concurrent.ConcurrentHashMap; // ← ADDED
import java.time.Instant;                      // ← ADDED
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class TelegramBot extends TelegramLongPollingBot {
    private final Set<Long> subscribers = new HashSet<>();
    private Map<String, String> pollIdsMap = new HashMap<>();
    private final Map<String, java.util.List<PollMessageRef>> campaigns = new java.util.concurrent.ConcurrentHashMap<>();

    private final Map<String, PollResult> polls = new ConcurrentHashMap<>();

    // Constructor......................................................................................................
    public TelegramBot() {

    }

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

        java.util.List<PollMessageRef> campaign = campaigns.computeIfAbsent(
                question, q -> new java.util.concurrent.CopyOnWriteArrayList<>());

        for (Long subscriber : this.subscribers) {
            sendPoll.setChatId(String.valueOf(subscriber));
            System.out.println("About to send poll to chatId = " + subscriber);
            try {
                Message sentMessage = execute(sendPoll);

                Poll sentPoll = sentMessage.getPoll();
                String pollId = sentPoll.getId();
                String qText  = sentPoll.getQuestion();
                int messageId = sentMessage.getMessageId();
                long chatId   = subscriber;

                System.out.println("pollId: " + pollId);

                pollIdsMap.put(question, pollId);

                polls.put(pollId, new PollResult(pollId, qText, options));

                campaign.add(new PollMessageRef(pollId, chatId, messageId));

            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void onPollClosed(Poll poll) {
        String pollId = poll.getId();
        String question = poll.getQuestion();
        Integer totalVoters = poll.getTotalVoterCount();

        polls.compute(pollId, (id, existing) -> {
            if (existing == null) {
                PollResult pr = PollResult.fromPoll(poll);
                logPoll(pr);
                return pr;
            } else {
                existing.updateFromPoll(poll);
                logPoll(existing);
                return existing;
            }
        });
    }

    private void logPoll(PollResult pr) {
        System.out.println("==== Poll closed ====");
        System.out.println("poll_id: " + pr.getPollId());
        System.out.println("Question: " + pr.getQuestion());
        System.out.println("Total voters: " + pr.getTotalVoters());

        List<String> texts = pr.getOptionTexts();
        List<Integer> counts = pr.getCounts();
        List<Double> pcts = pr.getPercentages();

        for (int i = 0; i < texts.size(); i++) {
            System.out.printf("- %s: %d מצביעים (%.1f%%)%n",
                    texts.get(i),
                    counts.get(i),
                    pcts.get(i));
        }
    }

    private static class PollMessageRef {
        final String pollId;
        final long chatId;
        final int messageId;
        boolean closed;

        PollMessageRef(String pollId, long chatId, int messageId) {
            this.pollId = pollId;
            this.chatId = chatId;
            this.messageId = messageId;
            this.closed = false;
        }
    }

    private void tryCloseCampaignWhenAllAnswered(String question) {
        java.util.List<PollMessageRef> refs = campaigns.get(question);
        if (refs == null || refs.isEmpty()) return;

        for (PollMessageRef ref : refs) {
            PollResult pr = polls.get(ref.pollId);
            if (pr == null || pr.getTotalVoters() < 1) {
                return;
            }
        }

        for (PollMessageRef ref : refs) {
            if (ref.closed) continue;
            try {
                StopPoll stop = new StopPoll();
                stop.setChatId(String.valueOf(ref.chatId));
                stop.setMessageId(ref.messageId);
                execute(stop);
                ref.closed = true; // סימון מקומי
                System.out.println("Closed pollId=" + ref.pollId + " chatId=" + ref.chatId);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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
                System.out.println("[BOT] got poll update");
                Poll poll = update.getPoll();
                System.out.println("[BOT] isClosed = " + poll.getIsClosed());

                // ===== ADDED: לעדכן את מודל התוצאות גם כשהסקר עדיין פתוח =====
                polls.compute(poll.getId(), (id, existing) -> {
                    if (existing == null) return PollResult.fromPoll(poll);
                    existing.updateFromPoll(poll);
                    return existing;
                });

                Boolean isClosed = poll.getIsClosed();
                if (Boolean.TRUE.equals(isClosed)) {
                    // סגור: הזרימה הקיימת שלך
                    onPollClosed(poll);
                } else {
                    tryCloseCampaignWhenAllAnswered(poll.getQuestion());
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

    public Set<Long> getSubscribers() {
        return subscribers;
    }
    public PollResult getPoll(String pollId) {
        return polls.get(pollId);
    }
    public Map<String, PollResult> getAllPolls() {
        return Map.copyOf(polls);
    }

    public static class PollResult {
        private final String pollId;
        private String question;
        private final List<String> optionTexts;
        private final List<Integer> counts;
        private int totalVoters;
        private boolean closed;
        private Instant updatedAt;

        public PollResult(String pollId, String question, List<String> optionTexts) {
            this.pollId = pollId;
            this.question = question;
            this.optionTexts = new java.util.ArrayList<>(optionTexts);
            this.counts = new java.util.ArrayList<>(java.util.Collections.nCopies(optionTexts.size(), 0));
            this.totalVoters = 0;
            this.closed = false;
            this.updatedAt = Instant.now();
        }

        public static PollResult fromPoll(Poll poll) {
            List<String> texts = new java.util.ArrayList<>();
            List<Integer> cnts = new java.util.ArrayList<>();
            for (PollOption opt : poll.getOptions()) {
                texts.add(opt.getText());
                cnts.add(opt.getVoterCount() != null ? opt.getVoterCount() : 0);
            }
            PollResult pr = new PollResult(poll.getId(), poll.getQuestion(), texts);
            pr.totalVoters = poll.getTotalVoterCount() != null ? poll.getTotalVoterCount() : 0;
            pr.closed = Boolean.TRUE.equals(poll.getIsClosed());
            pr.updatedAt = Instant.now();
            pr.counts.clear();
            pr.counts.addAll(cnts);
            return pr;
        }

        public void updateFromPoll(Poll poll) {
            this.question = poll.getQuestion();
            this.counts.clear();
            for (PollOption opt : poll.getOptions()) {
                this.counts.add(opt.getVoterCount() != null ? opt.getVoterCount() : 0);
            }
            this.totalVoters = poll.getTotalVoterCount() != null ? poll.getTotalVoterCount() : 0;
            this.closed = Boolean.TRUE.equals(poll.getIsClosed());
            this.updatedAt = Instant.now();
        }

        public List<Double> getPercentages() {
            List<Double> pct = new java.util.ArrayList<>(counts.size());
            for (int c : counts) {
                pct.add(totalVoters > 0 ? (c * 100.0 / totalVoters) : 0.0);
            }
            return pct;
        }

        // Getters & Setters................................................................................................
        public String getPollId() {
            return pollId;
        }

        public String getQuestion() {
            return question;
        }

        public List<String> getOptionTexts() {
            return java.util.Collections.unmodifiableList(optionTexts);
        }

        public List<Integer> getCounts() {
            return java.util.Collections.unmodifiableList(counts);
        }

        public int getTotalVoters() {
            return totalVoters;
        }

        public boolean isClosed() {
            return closed;
        }

        public Instant getUpdatedAt() {
            return updatedAt;
        }
    }
}