package utils;

import java.util.Random;
import java.util.UUID;

public class RandomDataGenerator {

    private static final Random random = new Random();

    private static final String[] SEARCH_TERMS = {
            "Harry Potter", "Agatha Christie", "Stephen King", "Tolkien", "krimi", "regény"
    };

    private static final String[] FIRST_NAMES = {
            "Anna", "Bela", "Csilla", "David", "Eva"
    };

    public static String randomEmail() {
        return "testuser_" + UUID.randomUUID().toString().substring(0, 8) + "@email.com";
    }

    public static String randomFirstName() {
        String name = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        return name + UUID.randomUUID().toString().substring(0, 4);
    }

    public static String randomSearchTerm() {
        return SEARCH_TERMS[random.nextInt(SEARCH_TERMS.length)];
    }

}
