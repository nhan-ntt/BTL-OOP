package commandLine;

import java.util.*;

public class Trie {
     /**
     * Node bao gom 1 char va meaning
     */

    static class Node {
        HashMap<Character, Node> children;
        //meaning: meaning of word ends at node
        String meaning;

        public Node() {
            children = new HashMap<>();
            meaning = null;
        }
    }

    public final Node root;

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
        node.meaning = null;
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
        if (node.meaning == null) {
            return "not found ψ(｀∇´)";
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
        if (node.meaning != null) {
            result.add(node.meaning);
        }
        return result;
    }


}