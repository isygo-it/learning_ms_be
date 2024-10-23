package eu.novobit.enumerations;

/**
 * The interface Enum content width.
 */
public interface IEnumContentWidth {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Full types.
         */
        FULL("full"),
        /**
         * Boxed types.
         */
        BOXED("boxed");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
