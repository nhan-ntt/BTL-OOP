package commandLine;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DictionaryCommandLine extends DictionaryManagement{
    /**
     * no   | english  | vietnamese.
     */
    public static void showAllWords() {
        System.out.print("No        | English             | Vietnamese\n\n");
        int id = 0;
        List<Word> words = listWord.getAllWords();
        for (Word w : words) {
            id++;
            System.out.printf("%-10d| %-20s| %s\n", id, w.getWordTarget(), w.getWordExplain());
        }
    }


    public static List<Word> dictionarySearcher(String word) {
        return listWord.recommendedList(word);
//        int id = 0;
//        for (Word w : recommendedList) {
//            id++;
//            System.out.printf("%-10d| %-20s| %s\n", id, w.getWordTarget(), w.getWordExplain());
//        }
    }



    /**
     * dictionary basic.
     */
    public static void dictionaryBasic() {
        System.out.println("Nhap vao so tu va danh sach tu dien:");
        insertFromCommandline();
        showAllWords();
    }

    /**
     * dictionary advanced.
     */

    public static void intro() {
        System.out.println("WELCOME !!!");
        System.out.println("[L] Lookup\n[S] Search all the relevant word\n[Show] Show All Words with explain");
        System.out.println("[A] Add a word\n[R] Remove a word\n[E] Edit meaning of word\n[F] Export this dictionary to file\n[X] Exit\n");
    }
    public static void dictionaryAdvanced() throws IOException {
        insertFromFileDICT();
        String choice, wordTarget, wordExplain;
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit)
        {
            intro();
            choice = sc.nextLine();
            switch (choice)
            {
                case "Show" :
                    showAllWords();
                    break;
                case "L" :
                    System.out.println("Write a word: ");
                    wordTarget = sc.nextLine();
                    wordTarget = lookupWord(wordTarget).getWordExplain();
                    if (!wordTarget.contentEquals("This word is not already existed"))
                        System.out.println("Explain: ");
                    System.out.println(wordTarget);
                    break;
                case "S" :
                    System.out.println("Write partial word: ");
                    wordTarget = sc.nextLine();
                    List<Word> recommendedList = dictionarySearcher(wordTarget);
                    int id = 0;
                    for (Word w : recommendedList) {
                        id++;
                        System.out.printf("%-10d| %-20s| %s\n", id, w.getWordTarget(), w.getWordExplain());
                    }
                    break;
                case "A" :
                    System.out.println("Write a word in english: ");
                    wordTarget = sc.nextLine();
//                    System.out.println("Write the phonetic of word: ");
//                    w1 = sc.nextLine();
                    System.out.println("Write the meaning of word: ");
                    wordExplain = sc.nextLine();
                    System.out.println(addWord(wordTarget, wordExplain));
                    break;
                case "R" :
                    System.out.println("Write a word: ");
                    wordTarget = sc.nextLine();
                    System.out.println(removeWord(wordTarget));
                    break;
                case "E" :
                    System.out.println("Write a word in english: ");
                    wordTarget = sc.nextLine();
                    System.out.println("Write the new meaning of word: ");
                    wordExplain = sc.nextLine();
                    System.out.println(editWord(wordTarget, wordExplain));
                    break;
                case "F" :
                    dictionaryExportToFile();
                    System.out.println("Export Successfully!");
                    break;
                case "X" :
                    exit = true;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        try {
            dictionaryAdvanced();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
