package eu.novobit.enumerations;


/**
 * The interface Enum civilities.
 */
public interface IEnumCivilities {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Single types.
         */
        SINGLE("Single"),
        /**
         * Maried types.
         */
        MARIED("Maried");

        private String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return this.meaning;
        }
    }
}
