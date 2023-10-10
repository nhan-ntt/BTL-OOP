package commandLine;

public class Word {
    private String word_target;
    private String word_explain;

    /**
     * Construct.
     */
    public Word() {
        this.word_target = "";
        this.word_explain = "";
    }
    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }
    public String getWord_target() {
        return word_target;
    }
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }
    public String getWord_explain() {
        return word_explain;
    }
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
    @Override
    public String toString() {
        return "Word{" + "word_target=" + word_target + ", word_explain=" + word_explain + '}';
    }

    public static void main(String[] args) {
        Word word = new Word();
        word.setWord_target("hello");
        word.setWord_explain("hello");
        System.out.println(word);
        System.out.println(word.toString());
        System.out.println(word.getWord_target());
    }
}