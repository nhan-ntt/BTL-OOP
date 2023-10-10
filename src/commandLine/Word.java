package commandLine;

import java.util.Objects;

/**
 * class Word chua tu va nghia
 */
public class Word {
    private String wordTarget;
    private String wordExplain;

    /**
     * Construct.
     */
    public Word() {
        this.wordTarget = "";
        this.wordExplain = "";
    }

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    @Override
    public String toString() {
        return "Word{" + "wordTarget=" + wordTarget + ", wordExplain=" + wordExplain + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(wordTarget, word.wordTarget) && Objects.equals(wordExplain, word.wordExplain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordTarget, wordExplain);
    }

    /**
     * main.
     */
    public static void main(String[] args) {
        Word word = new Word();
        word.setWordTarget("hello");
        word.setWordExplain("hello");
        System.out.println(word);
        System.out.println(word.toString());
        System.out.println(word.getWordTarget());
    }
}