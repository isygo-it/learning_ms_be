package eu.novobit.enumerations;


/**
 * The interface Enum target.
 */
public interface IEnumTarget {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 10;

    /**
     * The enum Types.
     */
    enum Types implements IEnum {
        /**
         * Company types.
         */
        COMPANY(""),    //All comany users
        /**
         * All companies types.
         */
        ALL_COMPANIES(""),  // All compnies
        /**
         * User types.
         */
        USER(""),
        /**
         * Amp role types.
         */
        AMP_ROLE(""),
        /**
         * Company role types.
         */
        COMPANY_ROLE("");

        private String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return this.meaning;
        }
    }
}
