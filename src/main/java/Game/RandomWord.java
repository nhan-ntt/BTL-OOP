package Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomWord {
    private String randomWord = "";

    public String getRandomWord() {
        return randomWord;
    }

    public void setRandomWord(String randomWord) {
        this.randomWord = randomWord;
    }

    public String WordAddLetter() {
        String filePath = "src\\main\\resources\\recentWord.txt";
        ArrayList<String> words = new ArrayList<>();

        randomWord="";
        // Đọc từ tệp và lưu vào danh sách
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            return null;
        }

        int randomPosition=0;
        char randomChar='h';
        while (randomWord.length() > 12 || randomWord.length()<=1 ) {
            Random random = new Random();
            int randomIndex = random.nextInt(words.size());
            randomWord = words.get(randomIndex);
           // System.out.println(randomWord);
            randomPosition = random.nextInt(randomWord.length());
            randomChar = (char) (random.nextInt(26) + 'a');
        }
        return randomWord.substring(0, randomPosition) + randomChar + randomWord.substring(randomPosition);
    }

    public static void main(String[] args) {
        RandomWord key = new RandomWord();
        System.out.println(key.WordAddLetter());
    }
}
