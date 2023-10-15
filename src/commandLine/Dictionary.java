package commandLine;

import java.util.LinkedList;

/**
 * class Dictionary Trie va co them cac list
 */
public class Dictionary {
    public static Trie listWord = new Trie();
    public static LinkedList<Word> favoriteWord = new LinkedList<Word>();
    public static LinkedList<Word> recentWord = new LinkedList<Word>();
    public static LinkedList<Word> removed = new LinkedList<Word>();
    public static LinkedList<Word> editedWord = new LinkedList<Word>();


    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        dictionary.listWord.insert("hello", "xin chao");
        dictionary.listWord.insert("dog", "con cho");
        dictionary.listWord.insert("hell", "dia nguc");
        dictionary.listWord.insert("damn", "đem");

        System.out.println(dictionary.listWord.recommendedList("d"));
        dictionary.listWord.remove("hello");
        System.out.println(dictionary.listWord.getMeaning("hello"));
    }
}