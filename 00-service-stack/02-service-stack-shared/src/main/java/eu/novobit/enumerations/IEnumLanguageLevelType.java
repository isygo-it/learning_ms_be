package eu.novobit.enumerations;

/**
 * The interface Enum language level type.
 */
public interface IEnumLanguageLevelType {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 12;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Fluent types.
         */
        FLUENT("Fluent"),
        /**
         * Alright types.
         */
        ALRIGHT("Alright"),
        /**
         * Good types.
         */
        GOOD("Good"),
        /**
         * Intermediate types.
         */
        INTERMEDIATE("Intermediate"),
        /**
         * Beginner types.
         */
        BEGINNER("Beginner"),
        ;
        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}


