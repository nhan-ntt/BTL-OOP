package commandLine;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * class DictionaryManagement quan ly insert file, export file
 */
public class DictionaryManagement {
    /**
     * insert from command line.
     */
    public static void insertFromCommandline(Dictionary dictionary) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Number of words:");
        int number = sc.nextInt();
        String wordTarget, wordExplain;
        while (number-- > 0) {
            System.out.println("Word:");
            wordTarget = sc.next();
            System.out.println("Meaning:");
            wordExplain = sc.next();
            dictionary.listWord.insert(wordTarget, wordExplain);
        }
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * insert from file Dict
     */
    public static void insertFromFileDICT(Dictionary dictionary) {
        try {
            String content = readFile("resources\\defaultDictionary.dict", Charset.defaultCharset());
            String[] words = content.split("@");
            for (String word : words) {
                String result[] = word.split("\r?\n", 2);
                if (result.length > 1) {
                    String wordExplain1 = new String();
                    String wordTarget1 = new String();
                    String wordSound1 = new String();
                    if (result[0].contains("/")) {
                        String firstmeaning = result[0].substring(0, result[0].indexOf("/"));
                        String lastSoundMeaning = result[0].substring(result[0].indexOf("/"), result[0].length());
                        wordTarget1 = firstmeaning;
                        wordSound1 = lastSoundMeaning;
                    } else {
                        wordTarget1 = result[0];
                        wordSound1 = "";
                    }
                    wordExplain1 = result[1];
                    dictionary.listWord.insert(wordTarget1.trim(), wordExplain1.trim());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        insertFromFileDICT(dictionary);
    }
}
