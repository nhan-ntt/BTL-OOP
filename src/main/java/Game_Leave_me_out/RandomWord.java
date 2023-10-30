package Game_Leave_me_out;

import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomWord {
    protected String randomWord;

    public String getRandomWord() {
        return randomWord;
    }

    public void setRandomWord(String randomWord) {
        this.randomWord = randomWord;
    }

    public String WordAddLetter()
    {
        String filePath = "src\\main\\resources\\recentWord.txt";
        ArrayList<String> words = new ArrayList<>();

        // Đọc từ tệp và lưu vào danh sách
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(words.size());
        randomWord = words.get(randomIndex);

        int randomPosition = random.nextInt(randomWord.length());
        char randomChar = (char) (random.nextInt(26) + 'a');
        return randomWord.substring(0, randomPosition) + randomChar + randomWord.substring(randomPosition);
    }

    public static void main(String[] args) {
        RandomWord key = new RandomWord();
        System.out.println(key.WordAddLetter());
    }
}
