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
                return "not found ψ(｀∇´)ψ";
            }
            node = node.children.get(c);
        }
        if (node == null || node.meaning == null) {
            return "not found ψ(｀∇´)ψ";
        }
        return node.meaning;
    }

    /**
     * tra ve list goi y khi search 1 tu.
     * @param word word.
     * @return result.
     */
    public List<String> recommendedList(String word) {
        List<String> result = new ArrayList<>();
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

    protected void recommendWord(Node node, String word, List<String> result) {
        if (node.meaning != null) {
            result.add(word);
        }
        for (char c: node.children.keySet()) {
            Node nextNode = node.children.get(c);
            recommendWord(nextNode, word + c, result);
        }
    }

    /**
     * main.
     */
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("hello", "xin chao");
        trie.insert("dog", "con cho");
        trie.insert("hell", "dia nguc");
        trie.insert("damn", "đem");

        System.out.println(trie.recommendedList("d"));
        trie.remove("hello");
        System.out.println(trie.getMeaning("hello"));
    }

}