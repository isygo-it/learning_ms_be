package eu.novobit.enumerations;

/**
 * The interface Enum algo digest config.
 */
public interface IEnumAlgoDigestConfig {
    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 7;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {

        /**
         * Md 2 types.
         */
        MD2("MD2"),
        /**
         * Md 5 types.
         */
        MD5("MD5"),
        /**
         * Sha types.
         */
        SHA("SHA"),
        /**
         * Sha 256 types.
         */
        SHA_256("SHA-256"),

        /**
         * Sha 384 types.
         */
        SHA_384("SHA-384"),

        /**
         * Sha 512 types.
         */
        SHA_512("SHA-512");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }


    }


}
