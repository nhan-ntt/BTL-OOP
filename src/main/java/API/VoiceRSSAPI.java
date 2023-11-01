package API;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class VoiceRSSAPI {

    public static void main(String[] args) {
       // String textToSpeech = "Chào bạn Ngọc Ánh";
        String textToSpeech = "i love you";
        String audioFilePath = generateTextToSpeech(textToSpeech, "English");
        if (audioFilePath != null) {
            System.out.println("Đường dẫn tệp âm thanh: " + audioFilePath);
        } else {
            System.out.println("Không thể tạo tệp âm thanh.");
        }
    }

    private static String generateTextToSpeech(String text, String language) {
        try {
            String apiKey = "b71ac35ba2a140d29a88ade10512d5e9";
            String apiUrl = "https://api.voicerss.org/";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            setConnectionProperties(connection);
            String data = "key=" + apiKey + "&src=" + text  ;
            if(language == "Vietnamese")  data = data + "&hl=vi-VN";
            sendDataToApi(connection, data);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                Path outputPath = Paths.get("output.mp3");
                Files.copy(is, outputPath, StandardCopyOption.REPLACE_EXISTING);
                is.close();
                connection.disconnect();
                return outputPath.toAbsolutePath().toString();
            } else {
                System.out.println("API request failed with HTTP code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void setConnectionProperties(HttpURLConnection connection) {
        try {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendDataToApi(HttpURLConnection connection, String data) {
        try {
            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
