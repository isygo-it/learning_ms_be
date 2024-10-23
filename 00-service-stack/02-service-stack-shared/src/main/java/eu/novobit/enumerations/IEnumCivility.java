package eu.novobit.enumerations;

/**
 * The interface Enum civility.
 */
public interface IEnumCivility {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 20;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * M types.
         */
        M("Married"),
        /**
         * S types.
         */
        S("Single"),
        /**
         * D types.
         */
        D("Divorced");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
