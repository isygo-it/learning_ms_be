package eu.novobit.enumerations;

/**
 * The interface Enum jwt storage.
 */
public interface IEnumJwtStorage {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 6;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * The Local.
         */
        LOCAL("Local storage"),
        /**
         * The Cookie.
         */
        COOKIE("Cookie storage");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }

}
