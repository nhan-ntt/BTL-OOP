package commandLine;

import java.io.IOException;
import java.util.LinkedList;

/**
 * class Dictionary Trie va co them cac list
 */
public class Dictionary {
    public static Trie listWord = new Trie();
    public static LinkedList<Word> favoriteWord = new LinkedList<Word>();
    public static LinkedList<Word> recentWord = new LinkedList<Word>();

    public static void main(String[] args) throws IOException {
        for (Word word : recentWord) {
            System.out.println(word.toString());
        }
    }
}