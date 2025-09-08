import java.util.Objects;

public class Subscriber {
    private long chatId;

    // Constructor......................................................................................................
    public Subscriber(long chatId) {
        this.chatId = chatId;
    }

    // toString.........................................................................................................

    // Methods..........................................................................................................
    /*
    דריסה למתודה equals כדי שבמחלקה TelegramBot ההשוואה בין נרשמים קיימים לנרשמים חדשים תוכל לקרות בתנאי הראשון לפי אובייקט
    ה-Susbscriber עצמו ולא לפי כתובתו בזיכרון.
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // אם זה אותו אובייקט
        if (!(o instanceof Subscriber)) return false; // אם זה לא Subscriber
        Subscriber other = (Subscriber) o;
        return chatId == other.chatId; // השוואה לפי chatId
    }

    /*
    דריסה של המתודה כדי לקבוע לפי מה האובייקט יקבל את קוד הגיבוב שלו ומאיפה המתודה equals צריכה להתחיל לבדוק אובייקטים זהים.
    */
    @Override
    public int hashCode() {
        return Objects.hash(chatId); // ייחודי לפי chatId
    }

    // Getters & Setters................................................................................................
    public long getChatId() {
        return chatId;
    }
}
