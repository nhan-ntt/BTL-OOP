package commandLine;

import java.util.LinkedList;

/**
 * class Dictionary Trie va co them cac list
 */
public class Dictionary {
    public Trie listWord = new Trie();
    public LinkedList<String> favoriteWord = new LinkedList<String>();
    public LinkedList<String> recentWord = new LinkedList<String>();
    public LinkedList<String> removed = new LinkedList<String>();
    public LinkedList<String> editedWord = new LinkedList<String>();


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