import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SurveyBot {

    private static final String BASE_URL = "https://app.seker.live/fm1/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("הכנס תעודת זהות: ");
        String id = scanner.nextLine();

        System.out.print("הכנס נושא לסקר: ");
        String topic = scanner.nextLine();

        try {
            // שלב 1: ניקוי היסטוריה
            clearHistory(id);

            // שלב 2: בניית הודעה
            String text = String.format("""
                צור סקר בנושא "%s".
                הסקר צריך לכלול 3 שאלות ו-4 אפשרויות תשובה לכל שאלה (א', ב', ג', ד').
                החזר את הסקר בפורמט CSV עם העמודות:
                Question,OptionA,OptionB,OptionC,OptionD
                """, topic);

            // שלב 3: שליחת בקשה
            String surveyResponse = sendMessage(id, text);

            System.out.println("\n✅ סקר שנוצר:");
            System.out.println(surveyResponse);

            // שלב 4: פירוק ה-CSV למבנה נתונים
            List<Question> surveyQuestions = parseCsvToQuestions(surveyResponse);

            // הדפסת שאלות ואפשרויות
            System.out.println("\n📋 שאלות שנוצרו:");
            for (int i = 0; i < surveyQuestions.size(); i++) {
                Question q = surveyQuestions.get(i);
                System.out.println((i + 1) + ". " + q.question);
                for (int j = 0; j < q.options.size(); j++) {
                    System.out.println("   " + (char)('A' + j) + ". " + q.options.get(j));
                }
            }

            // שלב 5: סימולציה של תשובות (סתם דוגמה לריכוז תוצאות)
            Map<Integer, Integer> votes = simulateVotes(surveyQuestions);

            // שלב 6: הצגת התוצאות
            System.out.println("\n📊 תוצאות הסקר:");
            showResults(surveyQuestions, votes);

        } catch (IOException e) {
            System.err.println("❌ שגיאה: " + e.getMessage());
        }
    }

    // ===== שליחת בקשות לשרת =====

    private static void clearHistory(String id) throws IOException {
        String params = "id=" + URLEncoder.encode(id, StandardCharsets.UTF_8);
        URL url = new URL(BASE_URL + "clear-history");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes(StandardCharsets.UTF_8));
        }

        readResponse(conn);
        System.out.println("🧹 ההיסטוריה נוקתה בהצלחה");
    }

    private static String sendMessage(String id, String text) throws IOException {
        String params = "id=" + URLEncoder.encode(id, StandardCharsets.UTF_8)
                + "&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8);

        URL url = new URL(BASE_URL + "send-message");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes(StandardCharsets.UTF_8));
        }

        return readResponse(conn);
    }

    private static String readResponse(HttpURLConnection conn) throws IOException {
        InputStream stream = (conn.getResponseCode() >= 400)
                ? conn.getErrorStream()
                : conn.getInputStream();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }
            return response.toString().trim();
        }
    }

    // ===== עיבוד ה-CSV =====

    private static List<Question> parseCsvToQuestions(String csvText) {
        List<Question> questions = new ArrayList<>();

        String[] lines = csvText.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            if (i == 0 && lines[i].toLowerCase().contains("question")) {
                // מדלגים על שורת כותרות אם יש
                continue;
            }
            String[] parts = lines[i].split(",", -1);
            if (parts.length >= 5) {
                String questionText = parts[0].trim();
                List<String> options = new ArrayList<>();
                for (int j = 1; j <= 4; j++) {
                    options.add(parts[j].trim());
                }
                questions.add(new Question(questionText, options));
            }
        }
        return questions;
    }

    // ===== ריכוז תוצאות =====

    private static Map<Integer, Integer> simulateVotes(List<Question> questions) {
        // סימולציה: כל שאלה מקבלת תשובה רנדומלית
        Random random = new Random();
        Map<Integer, Integer> votes = new HashMap<>();

        for (int i = 0; i < questions.size(); i++) {
            int randomAnswer = random.nextInt(4); // 0-3
            votes.put(i, randomAnswer);
        }
        return votes;
    }

    private static void showResults(List<Question> questions, Map<Integer, Integer> votes) {
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int chosen = votes.get(i);
            System.out.println("🟩 שאלה " + (i + 1) + ": " + q.question);
            for (int j = 0; j < q.options.size(); j++) {
                String prefix = (j == chosen) ? "✅ " : "   ";
                System.out.println(prefix + (char)('A' + j) + ". " + q.options.get(j));
            }
            System.out.println();
        }
    }

    // ===== מחלקת עזר לשאלה =====

    static class Question {
        String question;
        List<String> options;

        Question(String question, List<String> options) {
            this.question = question;
            this.options = options;
        }
    }
}
