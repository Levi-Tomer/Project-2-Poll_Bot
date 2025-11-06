import org.telegram.telegrambots.meta.api.objects.polls.Poll;
import org.telegram.telegrambots.meta.api.objects.polls.PollOption;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PollResult {
    private final String pollId;
    private String question;
    private final List<String> optionTexts;
    private final List<Integer> counts;
    private int totalVoters;
    private boolean closed;
    private Instant updatedAt;

    // Constructor......................................................................................................
    public PollResult(String pollId, String question, List<String> optionTexts) {
        this.pollId = pollId;
        this.question = question;
        this.optionTexts = new ArrayList<>(optionTexts);
        this.counts = new ArrayList<>(Collections.nCopies(optionTexts.size(), 0));
        this.totalVoters = 0;
        this.closed = false;
        this.updatedAt = Instant.now();
    }

    // Methods..........................................................................................................
    public static PollResult fromPoll(Poll poll) {
        List<String> texts = new ArrayList<>();
        List<Integer> cnts = new ArrayList<>();
        for (PollOption opt : poll.getOptions()) {
            texts.add(opt.getText());
            cnts.add(opt.getVoterCount() != null ? opt.getVoterCount() : 0);
        }
        PollResult pr = new PollResult(poll.getId(), poll.getQuestion(), texts);
        pr.totalVoters = poll.getTotalVoterCount() != null ? poll.getTotalVoterCount() : 0;
        pr.closed = Boolean.TRUE.equals(poll.getIsClosed());
        pr.updatedAt = Instant.now();
        // להחליף את ה-0-ים בספירות האמיתיות
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
        List<Double> pct = new ArrayList<>(counts.size());
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
        return Collections.unmodifiableList(optionTexts);
    }

    public List<Integer> getCounts() {
        return Collections.unmodifiableList(counts);
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
