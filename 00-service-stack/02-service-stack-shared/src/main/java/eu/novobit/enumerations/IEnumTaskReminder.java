package eu.novobit.enumerations;

/**
 * The interface Enum task reminder.
 */
public interface IEnumTaskReminder {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    enum Types implements IEnum {
        /**
         * Daily types.
         */
        DAILY(""),
        /**
         * Weekly types.
         */
        WEEKLY(""),
        /**
         * Monthly types.
         */
        MONTHLY(""),
        /**
         * Yearly types.
         */
        YEARLY("");

        private String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return this.meaning;
        }
    }
}
