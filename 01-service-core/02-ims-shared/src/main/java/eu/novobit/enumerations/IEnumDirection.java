package eu.novobit.enumerations;

/**
 * The interface Enum direction.
 */
public interface IEnumDirection {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Ltr types.
         */
        LTR("ltr"),
        /**
         * Rtl types.
         */
        RTL("rtl");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
