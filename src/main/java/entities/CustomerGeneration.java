package src.entities;

import src.entities.types.*;
import java.util.Random;

public class CustomerGeneration {
    private  static final String[] NAMES = {
            "Kenji", "Julius", "Kharl", "Patrick", "Isaiah", "Alexis", "Kylle", "Earl", "Francis", "Jay", "Vince";
    };

    private static final Random random = new Random();

    public static Customer spawnRandomCustomer(){
        String randomName = NAMES[random.nextInt(NAMES.length)];
        int roll = random.nextInt(100);

        if (roll < 50) {
            return new RegularCustomer(randomName);
        } else if (roll < 75) {
            return new StudentCustomer(randomName);
        } else if (roll < 90) {
            return new RichCustomer(randomName);
        } else {
            return new KarenCustomer("Karen");
        }
    }
}