package eu.novobit.enumerations;


/**
 * The interface Enum binary status.
 */
public interface IEnumJobStatType {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 30;

    /**
     * The enum Types.
     */
    enum Types implements IEnum {
        /**
         * Total count types.
         */
        TOTAL_COUNT("TOTAL_COUNT"),
        /**
         * Active count types.
         */
        ACTIVE_COUNT("ACTIVE_COUNT"),
        /**
         * Confirmed count types.
         */
        CONFIRMED_COUNT("CONFIRMED_COUNT");
        private String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return this.meaning;
        }
    }
}
