package eu.novobit.enumerations;

/**
 * The interface Enum logical ope.
 */
public interface IEnumLogicalOpe {

    /**
     * The constant STR_ENUM_SIZE.
     */
    public final static int STR_ENUM_SIZE = 6;

    /**
     * The enum Types.
     */
    public enum Types implements IEnum {
        /**
         * And types.
         */
        AND("AND"),
        /**
         * Or types.
         */
        OR("OR");

        private final String meaning;

        private Types(String meaning) {
            this.meaning = meaning;
        }

        public String meaning() {
            return meaning;
        }
    }
}
