package eu.novobit.enumerations;

/**
 * The interface Enum social status.
 */
public interface IEnumSocialStatus {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 4;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * Mme types.
         */
        MME("Madame"),
        /**
         * Mlle types.
         */
        MLLE("Mademoiselle");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
