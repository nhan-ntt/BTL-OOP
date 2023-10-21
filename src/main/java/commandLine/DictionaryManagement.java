package commandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * class DictionaryManagement quan ly insert file, export file
 */
public class DictionaryManagement extends Dictionary{
    /**
     * insert from command line.
     */
    public static void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Number of words:");
        int number = sc.nextInt();
        String wordTarget, wordExplain;
        sc.nextLine();
        while (number-- > 0) {
            System.out.println("Word:");
            wordTarget = sc.nextLine();
            System.out.println("Meaning:");
            wordExplain = sc.nextLine();
            listWord.insert(wordTarget, wordExplain);
        }
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     * insert from file Dict
     */
    public static void insertFromFileDICT() {
        try {
            String content = readFile("src\\main\\resources\\defaultDictionary.dict", Charset.defaultCharset());
            String[] words = content.split("@");
            for (String word : words) {
                String[] result = word.split("\r?\n", 2);
                if (result.length > 1) {
                    String wordExplain1 = new String();
                    String wordTarget1 = new String();
                    String wordSound1 = new String();
                    if (result[0].contains("/")) {
                        String firstMeaning = result[0].substring(0, result[0].indexOf("/"));
                        String lastSoundMeaning = result[0].substring(result[0].indexOf("/"), result[0].length());
                        wordTarget1 = firstMeaning;
                        wordSound1 = lastSoundMeaning;
                    } else {
                        wordTarget1 = result[0];
                        wordSound1 = "";
                    }
                    wordExplain1 = result[1];
                    listWord.insert(wordTarget1.trim(), wordExplain1.trim());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void dictionaryExportToFile() throws IOException {
        FileWriter fw = new FileWriter("src\\main\\resources\\dictionary.txt");
        List<Word> words = listWord.getAllWords();
        for (Word w : words) {
            fw.write(w.toString());
        }
        fw.close();
    }

    /**
     * write file recentWord and favoriteWord.
     */
    public static void exportCustomDictionary() throws IOException
    {
        FileWriter fw = new FileWriter("src\\main\\resources\\recentWord.txt");
        for(Word w : recentWord ) {
            fw.write(w.getWordTarget() + "\n");
        }
        fw.close();

        fw = new FileWriter("src\\main\\resources\\favoriteWord.txt");
        for(Word w : favoriteWord ) {
            fw.write(w.getWordTarget() + "\n");
        }
        fw.close();
    }

    /**
     * nhap du lieu cu cua nguoi dung
     * @throws IOException
     */
    public static void importCustomDictionary() throws IOException
    {
        // Read favoriteWord.txt
        File text = new File("src\\main\\resources\\favoriteWord.txt");
        Scanner sc = new Scanner(text);
        while (sc.hasNextLine()) {
            String target = sc.nextLine();
            if(target.isEmpty()) {
                break;
            }
            Word favWord = lookupWord(target);
            favoriteWord.addFirst(favWord);

            favWord.setFavorite(true);
        }

        // Read recentWord.txt
        text = new File("src\\main\\resources\\recentWord.txt");
        sc = new Scanner(text);
        while (sc.hasNextLine()) {
            String target = sc.nextLine();
            if(target.isEmpty()) {
                break;
            }
            recentWord.addFirst(lookupWord(target));
        }
    }

    public static void resetToDefaultDictionary()
    {
        recentWord.clear();
        favoriteWord.clear();
        listWord = listWord.clear();
        insertFromFileDICT();
    }

    /**
     * dictionary lookup.
     */
    public static Word lookupWord(String word) {
        if (!listWord.contains(word)) {
            return new Word(word, "This word is not already existed");
        }
        return new Word(word, listWord.getMeaning(word));
    }


    public static String addWord(String wordTarget, String wordExplain) {
        listWord.insert(wordTarget, wordExplain);
        return "Add word successfully!";
    }

    public static String removeWord(String wordTarget) {
        listWord.remove(wordTarget);
        return "Remove word successfully!";
    }

    public static String editWord(String wordTarget, String newMeaning) {
        listWord.changeMeaning(wordTarget, newMeaning);
        return "Edit word successfully!";
    }
}
