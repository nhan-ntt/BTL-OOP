package commandLine;

import java.util.*;

/**
 * class Trie la bst chua tu.
 */
public class Trie {
    /**
    * Node bao gom 1 char va meaning
    */
    private static class Node {
        HashMap<Character, Node> children;
        //meaning: meaning of word ends at node
        String meaning;

        public Node() {
            children = new HashMap<>();
            meaning = null;
        }
    }

    private final Node root;

    public Trie() {
        root = new Node();
    }

    public Node getRoot() {
        return root;
    }

    /**
     * check a word is in trie or not.
     */
    public boolean contains(String wordTarget) {
        Node node = root;
        for (int i = 0; i < wordTarget.length(); i++) {
            char c = wordTarget.charAt(i);
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }
        return node != null && node.meaning != null;
    }

    /**
     * insert a Word to Trie
     * @param wordTarget word-target.
     * @param wordExplain meaning.
     */
    public void insert(String wordTarget, String wordExplain) {
        Node node = root;
        for (int i = 0; i < wordTarget.length(); i++) {
            char c = wordTarget.charAt(i);
            if (!node.children.containsKey(c)) {
                node.children.put(c, new Node());
            }
            node = node.children.get(c);
        }
        node.meaning = wordExplain;
    }

    /**
     * remove a Word from trie.
     * @param wordTarget word.
     */
    public void remove(String wordTarget) {
        Node node = root;
        for (int i = 0; i < wordTarget.length(); i++) {
            char c = wordTarget.charAt(i);
            if (!node.children.containsKey(c)) {
                return;
            }
            node = node.children.get(c);
        }
        assert node != null;
        node.meaning = null;
        node = null;
    }

    /**
     * change meaning.
     * @param wordTarget word.
     */
    public void changeMeaning(String wordTarget, String newMeaning) {
        Node node = root;
        for (int i = 0; i < wordTarget.length(); i++) {
            char c = wordTarget.charAt(i);
            if (!node.children.containsKey(c)) {
                return;
            }
            node = node.children.get(c);
        }
        node.meaning = newMeaning;
    }

    /**
     * get meaning of a word.
     * @param wordTarget word.
     * @return meaning of word.
     */
    public String getMeaning(String wordTarget) {
        Node node = root;
        for (int i = 0; i < wordTarget.length(); i++) {
            char c = wordTarget.charAt(i);
            if (!node.children.containsKey(c)) {
                return "not found";
            }
            node = node.children.get(c);
        }
        if (node == null || node.meaning == null) {
            return "not found";
        }
        return node.meaning;
    }

    /**
     * tra ve list goi y khi search 1 tu.
     * @param word word.
     * @return result.
     */
    public List<Word> recommendedList(String word) {
        List<Word> result = new ArrayList<>();
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!node.children.containsKey(c)) {
                return result;
            }
            node = node.children.get(c);
        }
        recommendWord(node, word, result);
        return result;
    }

    protected void recommendWord(Node node, String word, List<Word> result) {
        if (node.meaning != null) {
            Word newWord = new Word(word, node.meaning);
            result.add(newWord);
        }
        for (char c : node.children.keySet()) {
            Node nextNode = node.children.get(c);
            recommendWord(nextNode, word + c, result);
        }
    }

    /**
     * get all words.
     * @return list of all words.
     */
    public List<Word> getAllWords() {
        List<Word> result = new ArrayList<>();
        trieToList(root, "", result);
        return result;
    }

    /**
     * trie to list.
     */
    public void trieToList(Node current, String prefix, List<Word> result) {
        if (current.meaning != null) {
            Word newWord = new Word(prefix, current.meaning);
            result.add(newWord);
        }
        for (char c : current.children.keySet()) {
            Node nextNode = current.children.get(c);
            trieToList(nextNode, prefix + c, result);
        }
    }

    public Trie clear() {
        return new Trie();
    }
}