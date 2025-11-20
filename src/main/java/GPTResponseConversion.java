import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class GPTResponseConversion {
    private String question1;
    private String question2;
    private String question3;

    private List<String> options1;
    private List<String> options2;
    private List<String> options3;

    public GPTResponseConversion(String jsonText) {
        options1 = new ArrayList<>();
        options2 = new ArrayList<>();
        options3 = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonText);
            JsonNode pollsArray = root.get("polls");

            if (pollsArray != null && pollsArray.isArray()) {
                for (int i = 0; i < pollsArray.size() && i < 3; i++) {
                    JsonNode poll = pollsArray.get(i);
                    String question = poll.get("question").asText();
                    JsonNode opts = poll.get("options");

                    List<String> optionList = new ArrayList<>();
                    if (opts != null && opts.isArray()) {
                        for (JsonNode opt : opts) {
                            optionList.add(opt.asText());
                        }
                    }

                    switch (i) {
                        case 0 -> {
                            question1 = question;
                            options1 = optionList;
                        }
                        case 1 -> {
                            question2 = question;
                            options2 = optionList;
                        }
                        case 2 -> {
                            question3 = question;
                            options3 = optionList;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON in GeneratedPollSet:");
            e.printStackTrace();
        }
    }

    // === מתודה נוחה להדפסה / בדיקה ===
    @Override
    public String toString() {
        return """
                Poll 1:
                  Q: %s
                  Options: %s
                Poll 2:
                  Q: %s
                  Options: %s
                Poll 3:
                  Q: %s
                  Options: %s
                """.formatted(
                question1, options1,
                question2, options2,
                question3, options3
        );
    }

    // === Getters ===
    public String getQuestion1() {
        return question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public List<String> getOptions1() {
        return options1;
    }

    public List<String> getOptions2() {
        return options2;
    }

    public List<String> getOptions3() {
        return options3;
    }




}
