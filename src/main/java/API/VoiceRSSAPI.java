package API;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class VoiceRSSAPI {
    public static void main(String[] args) {
        String apiKey = "b71ac35ba2a140d29a88ade10512d5e9";
        String textToSpeech = "Congratulations, your account has been activated successfully!";

        try {
            String apiUrl = "https://api.voicerss.org/";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String data = "key=" + apiKey + "&src=" + textToSpeech;
            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                Path outputPath = Paths.get("output.mp3");
                Files.copy(is, outputPath, StandardCopyOption.REPLACE_EXISTING);
                is.close();
                System.out.println("Tệp âm thanh đã được tải về: " + outputPath.toAbsolutePath());
            } else {
                System.out.println("API request failed with HTTP code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
