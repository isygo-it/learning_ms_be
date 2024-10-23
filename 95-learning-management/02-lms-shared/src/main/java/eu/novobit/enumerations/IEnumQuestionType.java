package eu.novobit.enumerations;


/**
 * The interface Enum question type.
 */
public interface IEnumQuestionType {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * The Mcq.
         */
        MCQ("Multiple choice question"),
        /**
         * The Taq.
         */
        TAQ("Text answer question"),
        /**
         * The Mctaq.
         */
        MCTAQ("Multiple choice with text answer question");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
