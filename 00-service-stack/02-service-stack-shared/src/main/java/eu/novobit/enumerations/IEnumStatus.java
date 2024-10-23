package eu.novobit.enumerations;

/**
 * The interface Enum status.
 */
public interface IEnumStatus {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Active types.
         */
        ACTIVE("Active"),
        /**
         * Inactive types.
         */
        INACTIVE("Inactive");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
