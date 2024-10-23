package eu.novobit.enumerations;


/**
 * The interface Enum password status.
 */
public interface IEnumPasswordStatus {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    enum Types implements IEnum {

        /**
         * Bad types.
         */
        BAD("BAD"),
        /**
         * Locked types.
         */
        LOCKED("LOCKED"),
        /**
         * Valid types.
         */
        VALID("VALID"),
        /**
         * Expired types.
         */
        EXPIRED("EXPIRED"),
        /**
         * Broken types.
         */
        BROKEN("BROKEN"),
        /**
         * Deprecated types.
         */
        DEPRECATED("DEPRECATED"),
        /**
         * Unauthorized types.
         */
        UNAUTHORIZED("UNAUTHORIZED");

        private String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return this.meaning;
        }
    }
}