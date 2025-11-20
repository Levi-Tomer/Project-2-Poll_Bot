import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ChatGPTApi {
    private static final String BASE_URL = "https://app.seker.live/fm1/";
    private final String id ="323944728" ;
    private String lastQuestion;
    private List<String> lastOptions;
    private String jsonResponse;


    public ChatGPTApi() {

    }

    // שליחת הודעה ל-ChatGPT לקבלת סקר
    public boolean requestPollFromTopic(String topic) {
        try {
            System.out.println("Sending topic to ChatGPT API through seker.live...");
            System.out.println("Topic: " + topic);

            String endpoint = BASE_URL + "send-message";
            String prompt = String.format(
                    "Return only valid JSON with exactly this structure: " +
                            "{ \"polls\": [" +
                            "{\"question\":\"Q1\",\"options\":[\"A\",\"B\",\"C\",\"D\"]}," +
                            "{\"question\":\"Q2\",\"options\":[\"A\",\"B\",\"C\",\"D\"]}," +
                            "{\"question\":\"Q3\",\"options\":[\"A\",\"B\",\"C\",\"D\"]}" +
                            "] } " +
                            "All questions must be about the topic: %s", topic);


            String body = "id=" + id + "&text=" + java.net.URLEncoder.encode(prompt, StandardCharsets.UTF_8);

            // --- ניסיון ראשון: POST ---
            HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            System.out.println("📩 Response code (POST): " + responseCode);

            // אם השרת לא מצא את הנתיב או דחה את הבקשה — ננסה GET
            if (responseCode == 404 || responseCode == 405) {
                System.out.println("Retrying with GET method...");
                String urlWithParams = endpoint + "?id=" + id + "&text=" +
                        java.net.URLEncoder.encode(prompt, StandardCharsets.UTF_8);
                conn = (HttpURLConnection) new URL(urlWithParams).openConnection();
                conn.setRequestMethod("GET");
                responseCode = conn.getResponseCode();
                System.out.println("Response code (GET): " + responseCode);
            }

            if (responseCode != 200) {
                System.err.println("API request failed (code " + responseCode + ")");
                return false;
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) response.append(line);
            }

            jsonResponse = response.toString().trim();
            System.out.println("JSON received:\n" + jsonResponse);
            GPTResponseConversion gptResponseConversion = new GPTResponseConversion(jsonResponse);



            return jsonResponse.startsWith("{") && jsonResponse.contains("polls");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public GPTResponseConversion generatePollSetFromTopic(String topic) {
        try {
            System.out.println("Sending topic to ChatGPT API: " + topic);

            String endpoint = BASE_URL + "send-message";
            String prompt = String.format(
                    "Return valid JSON with exactly this structure: " +
                            "{ \"polls\": [" +
                            "{\"question\":\"Q1\",\"options\":[\"A\",\"B\",\"C\",\"D\"]}," +
                            "{\"question\":\"Q2\",\"options\":[\"A\",\"B\",\"C\",\"D\"]}," +
                            "{\"question\":\"Q3\",\"options\":[\"A\",\"B\",\"C\",\"D\"]}" +
                            "] } " +
                            "All questions must be about the topic: %s", topic);

            // נוודא שנשלחים גם id וגם text, כמו שהשרת מצפה
            String body = "id=" + id + "&text=" + java.net.URLEncoder.encode(prompt, StandardCharsets.UTF_8);

            HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response code from server: " + responseCode);
            if (responseCode != 200) {
                System.err.println("ChatGPT API returned code " + responseCode);
                return null;
            }

            // קריאת תשובת השרת
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            String jsonText = response.toString().trim();
            System.out.println("JSON received:\n" + jsonText);

            // יצירת אובייקט GPTResponseConversion מהתשובה
            GPTResponseConversion pollSet = new GPTResponseConversion(jsonText);
            return pollSet;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    // ניקוי היסטוריה
    public void clearHistory() {
        try {
            String endpoint = BASE_URL + "clear-history";
            String body = "id=" + id;
            HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }

            System.out.println("ChatGPT history cleared.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // בדיקת יתרת הודעות
    public void checkBalance() {
        try {
            String endpoint = BASE_URL + "check-balance?id=" + id;
            HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = br.readLine();
            System.out.println("Remaining balance: " + response);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ניתוח טקסט לתוך שאלה ותשובות
    private void parsePollText(String gptText) {
        try {
            String[] lines = gptText.split("\n");
            lastQuestion = null;
            lastOptions = new ArrayList<>();

            for (String line : lines) {
                line = line.trim();
                if (line.toLowerCase().startsWith("question:") || line.toLowerCase().startsWith("שאלה:")) {
                    lastQuestion = line.substring(line.indexOf(':') + 1).trim();
                } else if (line.toLowerCase().startsWith("option") || line.contains("1.") || line.contains("-")) {
                    String clean = line.replaceAll("^(Option|[0-9]\\.|-|•)\\s*", "").trim();
                    if (!clean.isEmpty()) lastOptions.add(clean);
                }
            }

            System.out.println("Parsed Question: " + lastQuestion);
            System.out.println("Parsed Options: " + lastOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // גישה לשאלה האחרונה שנוצרה
    public String getLastQuestion() {
        return lastQuestion;
    }

    public List<String> getLastOptions() {
        return lastOptions;
    }

    public String getJsonResponse() {
        return jsonResponse;
    }

    public String getId() {
        return id;
    }
}
