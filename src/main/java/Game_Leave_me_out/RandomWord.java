package Game_Leave_me_out;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomWord {
    public static void main(String[] args) {
        // Đường dẫn đến tệp RecentWord.txt
        String filePath = "src\\main\\resources\\recentWord.txt";

        // Tạo danh sách để lưu các từ từ tệp
        ArrayList<String> words = new ArrayList<>();

        // Đọc từ tệp và lưu vào danh sách
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Kiểm tra xem danh sách có từ nào không
        if (!words.isEmpty()) {
            // Tạo một đối tượng Random để sinh số ngẫu nhiên
            Random random = new Random();

            // Ngẫu nhiên chọn một chỉ mục từ danh sách
            int randomIndex = random.nextInt(words.size());

            // Lấy từ tương ứng với chỉ mục ngẫu nhiên
            String randomWord = words.get(randomIndex);

        } else {
            System.out.println("Tệp RecentWord.txt không chứa từ nào.");
        }
    }
}
