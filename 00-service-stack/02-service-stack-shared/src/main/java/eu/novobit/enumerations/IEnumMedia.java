package eu.novobit.enumerations;

/**
 * The interface Enum media.
 */
public interface IEnumMedia {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 16;
    /**
     * The constant APPLICATION_JSON.
     */
    final static String APPLICATION_JSON = "application/json";

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Application json types.
         */
        APPLICATION_JSON(IEnumMedia.APPLICATION_JSON);

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
