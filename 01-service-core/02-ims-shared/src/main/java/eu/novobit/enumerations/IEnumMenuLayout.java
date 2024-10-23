package eu.novobit.enumerations;

/**
 * The interface Enum menu layout.
 */
public interface IEnumMenuLayout {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Vertical types.
         */
        VERTICAL("vertical"),
        /**
         * Horizontal types.
         */
        HORIZONTAL("horizontal");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
