package eu.novobit.enumerations;


/**
 * The interface Enum binary status.
 */
public interface IEnumBinaryStatus {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    enum Types implements IEnum {
        /**
         * Enabled types.
         */
        ENABLED("ENABLED"),
        /**
         * Disabled types.
         */
        DISABLED("DISABLED");

        private String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return this.meaning;
        }
    }
}
