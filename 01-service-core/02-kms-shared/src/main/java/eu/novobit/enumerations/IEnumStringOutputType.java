package eu.novobit.enumerations;

/**
 * The interface Enum string output type.
 */
public interface IEnumStringOutputType {
    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 11;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {

        /**
         * Base 64 types.
         */
        Base64("Base64"),
        /**
         * Hexadecimal types.
         */
        Hexadecimal("Hexadecimal");


        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
