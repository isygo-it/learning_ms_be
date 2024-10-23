package eu.novobit.enumerations;

/**
 * The interface Enum skill level type.
 */
public interface IEnumSkillLevelType {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 12;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Expert types.
         */
        EXPERT("Expert"),
        /**
         * Confirmed types.
         */
        CONFIRMED("Confirmed"),
        /**
         * Intermediate types.
         */
        INTERMEDIATE("Intermediate"),
        /**
         * Beginner types.
         */
        BEGINNER("Beginner");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
